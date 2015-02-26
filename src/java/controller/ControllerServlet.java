/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cart.ShoppingCart;
import entity.Category;
import entity.Customer;
import entity.PasswordEncrypt;
import entity.Product;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.CategoryFacade;
import session.CustomerFacade;
import session.OrderManager;
import session.ProductFacade;
import validate.Validator;

/**
 *
 * @author tgiunipero
 */
@WebServlet(name = "Controller",
        loadOnStartup = 1,
        urlPatterns = {"/category",
            "/addToCart",
            "/viewCart",
            "/updateCart",
            "/checkout",
            "/purchase",
            "/chooseLanguage",
            "/register",
            "/registered",
            "/logout",
            "/login"})
public class ControllerServlet extends HttpServlet {

    private String surcharge;

    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private CategoryFacade categoryFacade;
    @EJB
    private ProductFacade productFacade;
    @EJB
    private OrderManager orderManager;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

        super.init(servletConfig);

        // store category list in servlet context
        getServletContext().setAttribute("categories", categoryFacade.findAll());
        
        surcharge = servletConfig.getServletContext().getInitParameter("deliverySurcharge");
        

    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        Category selectedCategory;
        Collection<Product> categoryProducts;

        // if category page is requested
        if (userPath.equals("/category")) {

            // get categoryId from request
            String categoryId = request.getQueryString();

            if (categoryId != null) {

                // get selected category
                selectedCategory = categoryFacade.find(Integer.parseInt(categoryId));

                // place selected category in session scope
                session.setAttribute("selectedCategory", selectedCategory);

                // get all products for selected category
                categoryProducts = selectedCategory.getProductCollection();

                // place category products in session scope
                session.setAttribute("categoryProducts", categoryProducts);

            }

            // if cart page is requested
        } else if (userPath.equals("/viewCart")) {

            String clear = request.getParameter("clear");

            if ((clear != null) && clear.equals("true")) {

                ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                cart.clear();
            }

            userPath = "/cart";

            // if checkout page is requested
        } else if (userPath.equals("/checkout")) {

            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            // calculate total
            cart.calculateTotal(surcharge);
            
            session.setAttribute("userStatus", "1");
            session.setAttribute("userEmail", request.getRemoteUser());

            // forward to checkout page and switch to a secure channel
            // if user switches language
        } else if (userPath.equals("/chooseLanguage")) {
            // TODO: Implement language request

        } else if (userPath.equals("/register")) {
            userPath = "/register";

        } else if (userPath.equals("/logout")) {
            session.setAttribute("userStatus", "0");
            session = request.getSession();
            session.invalidate();
            userPath = "/logout";

        } else if (userPath.equals("/login")) {

            session.setAttribute("userStatus", "1");
            session.setAttribute("userEmail", request.getRemoteUser());
            userPath = "/login";

        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");  // ensures that user input is interpreted as
        // 8-bit Unicode (e.g., for Czech characters)

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        Validator validator = new Validator();

        // if addToCart action is called
        if (userPath.equals("/addToCart")) {

            // if user is adding item to cart for first time
            // create cart object and attach it to user session
            if (cart == null) {

                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }

            // get user input from request
            String productId = request.getParameter("productId");

            if (!productId.isEmpty()) {

                Product product = productFacade.find(Integer.parseInt(productId));
                cart.addItem(product);
            }

            userPath = "/category";

        } //If registered action is called
        else if (userPath.equals("/registered")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String pnumber = request.getParameter("pnumber");
            String address = request.getParameter("address");

            PasswordEncrypt p = new PasswordEncrypt();

            String password = p.encrypt(request.getParameter("password"));

            int added = customerFacade.addCustomer(name, email, pnumber, address, password);

            request.setAttribute("name", name);
            request.setAttribute("address", address);
            request.setAttribute("email", email);
            request.setAttribute("phone", pnumber);

        } // if updateCart action is called
        else if (userPath.equals("/updateCart")) {

            // get input from request
            String productId = request.getParameter("productId");
            String quantity = request.getParameter("quantity");

            boolean invalidEntry = validator.validateQuantity(productId, quantity);

            if (!invalidEntry) {

                Product product = productFacade.find(Integer.parseInt(productId));
                cart.update(product, quantity);
            }

            userPath = "/cart";

            // if purchase action is called
        } else if (userPath.equals("/purchase")) {
            if (cart != null) {
                Customer customer = customerFacade.find(Integer.parseInt(request.getParameter("id")));

                int orderId = orderManager.placeOrder(customer, cart);

                if (orderId != 0) {
                    cart.clear();

                    // get order details
                    Map orderMap = orderManager.getOrderDetails(orderId);

                    // place order details in request scope
                    request.setAttribute("customer", orderMap.get("customer"));
                    request.setAttribute("products", orderMap.get("products"));
                    request.setAttribute("orderRecord", orderMap.get("orderRecord"));
                    request.setAttribute("orderedProducts", orderMap.get("orderedProducts"));

                    userPath = "/confirmation";
                }//end if
            }//end if

        }

        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
