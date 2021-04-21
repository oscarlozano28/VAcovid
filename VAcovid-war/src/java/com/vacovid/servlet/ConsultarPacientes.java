/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.servlet;

import com.vacovid.entity.Cita;
import com.vacovid.session.CitaFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(name = "ConsultarPacientes", urlPatterns = {"/ConsultarPacientes"})
public class ConsultarPacientes extends HttpServlet {

    @EJB
    private CitaFacadeLocal citaFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession objsession = request.getSession(false);
            String usuario = (String)objsession.getAttribute("usuario1");
            
            ArrayList<Cita> lista= new ArrayList();
            
            
            if (request.getParameter("action").equals("Consultar Pacientes sin vacunar")) 
            {
                for (Cita cita : citaFacade.findAll()) 
                {
                    if (cita.getIdSitio().getIdentificacionRepresentante().getIdentificacion() == Integer.parseInt(usuario) &&
                    cita.getFechaDate().compareTo(new Date())>=0)  
                    {
                        lista.add(cita);
                    }
                }
            }
            else if (request.getParameter("action").equals("Consultar Pacientes vacunados")) 
            {
                for (Cita cita : citaFacade.findAll()) 
                {
                    if (cita.getIdSitio().getIdentificacionRepresentante().getIdentificacion() == Integer.parseInt(usuario) &&
                    cita.getFechaDate().compareTo(new Date())<=0)  
                    {
                        lista.add(cita);
                    }
                }
            }
            if (lista.size() != 0) 
            {
                request.setAttribute("allCitas", lista);
                request.getRequestDispatcher("consultarPacientes.jsp").forward(request, response);
            }
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ConsultarPacientes</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("</body>");
            out.println("</html>");
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