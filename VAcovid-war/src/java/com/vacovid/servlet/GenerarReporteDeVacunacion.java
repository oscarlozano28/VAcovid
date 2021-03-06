/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.servlet;

import com.vacovid.entity.Cita;
import com.vacovid.entity.CorreoBean;
import com.vacovid.entity.ReporteDeVacunacion;
import com.vacovid.entity.Usuario;
import com.vacovid.entity.VacunaRecibida;
import com.vacovid.session.CitaFacadeLocal;
import com.vacovid.session.ReporteDeVacunacionFacadeLocal;
import com.vacovid.session.UsuarioFacadeLocal;
import com.vacovid.session.VacunaRecibidaFacadeLocal;
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
@WebServlet(name = "GenerarReporteDeVacunacion", urlPatterns = {"/GenerarReporteDeVacunacion"})
public class GenerarReporteDeVacunacion extends HttpServlet {

    @EJB
    private CorreoBean correoBean;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    @EJB
    private VacunaRecibidaFacadeLocal vacunaRecibidaFacade;

    @EJB
    private ReporteDeVacunacionFacadeLocal reporteDeVacunacionFacade;

    @EJB
    private CitaFacadeLocal citaFacade;

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
            
            HttpSession objsession = request.getSession(false);
            String usuario = (String)objsession.getAttribute("usuario1");
            
            Integer citaid = Integer.parseInt(request.getParameter("cita"));
            String brazo = request.getParameter("brazo");
            Integer vacunaid= Integer.parseInt(request.getParameter("nombreVacuna"));
            Usuario user = null;
            
            if (request.getParameter("action").equals("Generar")) 
            {
                Cita cita = citaFacade.find(citaid);
                user = usuarioFacade.find(cita.getIdentificacionUsuario().getIdentificacion());
                ReporteDeVacunacion reporte = null;
                for (ReporteDeVacunacion r : reporteDeVacunacionFacade.findAll()) {
                    if (r.getIdCita().getCitaid()==cita.getCitaid()) {
                        reporte = r;
                    }
                }
                reporte.setBrazo(brazo);
                VacunaRecibida v = vacunaRecibidaFacade.find(vacunaid);
                v.setCantidad(v.getCantidad() - 1);
                vacunaRecibidaFacade.edit(v);
                reporte.setVacuna(v.getNombre());
                reporteDeVacunacionFacade.edit(reporte);
                
                String reporteVacunacion="Querido usuario " +user.getApellido() +","+ user.getNombre() + " Su reporte se ha generado exitosamente en la";
                reporteVacunacion+=" Fecha: " + cita.getFecha() +" "+ cita.getHora() +" con el ";
                reporteVacunacion+=" Personal: " + reporte.getIdentificacionPersonal().getApellido() +","+reporte.getIdentificacionPersonal().getNombre()+" aplicando la";
                reporteVacunacion+=" Vacuna: " + v.getNombre()+" en el";
                reporteVacunacion+=" Brazo: " + reporte.getBrazo()+" con la";
                reporteVacunacion+=" Dosis: #" + cita.getDosis()+" en el";
                reporteVacunacion+=" Sitio: " + reporte.getIdentificacionPersonal().getIdSitio().getNombre();
                
                //Datos del correo que envia los codigos
                String vaEmail = "vacovidunipiloto@gmail.com";
                String username ="vacovidunipiloto";
                String pass = "123@Admin";
                
                correoBean.generarReporte(vaEmail, username, pass, user.getCorreo(), reporteVacunacion);
                
                out.println("<script type=\"text/javascript\">\n" + "  "
                                + "alert(\"Reporte generado exitosamente\");\n"
                                + "window.location.href =" + "\"http://localhost:8080/VAcovid-war/menu_personal.jsp\"" +
                                "</script>");
            }
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GenerarReporteDeVacunacion</title>");            
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
