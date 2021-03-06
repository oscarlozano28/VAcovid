/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.servlet;

import com.vacovid.session.DistribuidorFacadeLocal;
import com.vacovid.session.PersonalFacadeLocal;
import com.vacovid.session.RepresentanteFacadeLocal;
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
 * @author JEFRY
 */
@WebServlet(name = "LoginUsuario", urlPatterns = {"/LoginUsuario"})
public class LoginUsuario extends HttpServlet {

    @EJB
    private DistribuidorFacadeLocal distribuidorFacade;

    @EJB
    private PersonalFacadeLocal personalFacade;

    @EJB
    private RepresentanteFacadeLocal representanteFacade;

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
            
            Integer identificacion= Integer.parseInt(request.getParameter("identificacion"));
            String contra=request.getParameter("contra");
            
            if (request.getParameter("rol").equals("Usuario de vacunacion")) 
            {
                loginUs(identificacion, contra, request, response);
            }
            else if (request.getParameter("rol").equals("Representante de sitio")) 
            {
                loginRep(identificacion, contra, request, response);
            }
            else if (request.getParameter("rol").equals("Personal de vacunacion")) 
            {
                loginPe(identificacion, contra, request, response);
            }
            else if (request.getParameter("rol").equals("Distribuidor de vacunas")) 
            {
                loginDis(identificacion, contra, request, response);
            }
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginUsuario</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    public void loginUs(Integer identificacion, String contra, HttpServletRequest request, HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()) {
        if (usuarioFacade.find(identificacion)==null) 
            {
                 out.println("<script type=\"text/javascript\">\n" + "  alert(\"Usuario no registrado\");\n" + "</script>");
                 out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
            }
            else
            {
                if (usuarioFacade.find(identificacion).getPassword().equals(contra)) 
                {
                    HttpSession objsession = request.getSession(true);
                    objsession.setAttribute("usuario1", Integer.toString(identificacion));
                    response.sendRedirect("menu.jsp");
                    //out.println("Login Exitoso");
                }
                else
                {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Contrase??a incorrecta\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
                }
            }
        }
    }
    
    public void loginRep(Integer identificacion, String contra, HttpServletRequest request, HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()) {
        if (representanteFacade.find(identificacion)==null) 
            {
                 out.println("<script type=\"text/javascript\">\n" + "  alert(\"Usuario no registrado\");\n" + "</script>");
                 out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
            }
            else
            {
                if (representanteFacade.find(identificacion).getPassword().equals(contra)) 
                {
                    HttpSession objsession = request.getSession(true);
                    objsession.setAttribute("usuario1", Integer.toString(identificacion));
                    response.sendRedirect("menu_representante.jsp");
                    //out.println("Login Exitoso");
                }
                else
                {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Contrase??a incorrecta\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
                }
            }
        }
    }
    
    public void loginPe(Integer identificacion, String contra, HttpServletRequest request, HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()) {
        if (personalFacade.find(identificacion)==null) 
            {
                 out.println("<script type=\"text/javascript\">\n" + "  alert(\"Usuario no registrado\");\n" + "</script>");
                 out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
            }
            else
            {
                if (personalFacade.find(identificacion).getPassword().equals(contra)) 
                {
                    HttpSession objsession = request.getSession(true);
                    objsession.setAttribute("usuario1", Integer.toString(identificacion));
                    response.sendRedirect("menu_personal.jsp");
                    //out.println("Login Exitoso");
                }
                else
                {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Contrase??a incorrecta\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
                }
            }
        }
    }
    
    public void loginDis(Integer identificacion, String contra, HttpServletRequest request, HttpServletResponse response) throws IOException{
        try (PrintWriter out = response.getWriter()) {
        if (distribuidorFacade.find(identificacion)==null) 
            {
                 out.println("<script type=\"text/javascript\">\n" + "  alert(\"Usuario no registrado\");\n" + "</script>");
                 out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
            }
            else
            {
                if (distribuidorFacade.find(identificacion).getPassword().equals(contra)) 
                {
                    HttpSession objsession = request.getSession(true);
                    objsession.setAttribute("usuario1", Integer.toString(identificacion));
                    response.sendRedirect("menu_distribuidor.jsp");
                    //out.println("Login Exitoso");
                }
                else
                {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Contrase??a incorrecta\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/loginUsuario.jsp\" />");
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
