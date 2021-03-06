/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.servlet;

import com.vacovid.entity.Usuario;
import com.vacovid.session.UsuarioFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Cristian Duarte
 */
@WebServlet(name = "RecuperarContraseña", urlPatterns = {"/RecuperarContrase_a"})
public class RecuperarContraseña extends HttpServlet {

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            int id = Integer.parseInt(request.getParameter("id"));
            if (usuarioFacade.find(id) != null) {
                Usuario user = usuarioFacade.find(id);
                
                //confirma que las claves sean las mismas
                if (user.getClaveConfirmacion()== Integer.parseInt(request.getParameter("codigo"))) {
                    //se verifica que las contraseñas ingresadas coincidan
                    if (request.getParameter("contraNueva").equals(request.getParameter("contraConfirmar"))) {
                        //se ajusta la nueva contraseña
                        user.setPassword(request.getParameter("contraNueva"));
                        usuarioFacade.edit(user);
                        out.println("<script type=\"text/javascript\">\n" + "  "
                                + "alert(\"Contraseña Actualizada\");\n"
                                + "window.location.href =" + "\"http://localhost:8080/VAcovid-war/loginUsuario.jsp\""
                                + "</script>");
                    } else {
                        out.println("<script type=\"text/javascript\">\n" + "  alert(\"Las contraseñas no coinciden\");\n" + "</script>");
                        out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/recuperaContrasena.jsp\" />");
                    }
                } else {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Codigo incorrecto\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/recuperaContrasena.jsp\" />");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
