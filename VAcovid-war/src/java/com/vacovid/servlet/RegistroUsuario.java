/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vacovid.servlet;


import com.vacovid.entity.Usuario;
import com.vacovid.session.MunicipioFacadeLocal;
import com.vacovid.session.UsuarioFacadeLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JEFRY
 */
@WebServlet(name = "RegistroUsuario", urlPatterns = {"/RegistroUsuario"})
public class RegistroUsuario extends HttpServlet {

    @EJB
    private MunicipioFacadeLocal municipioFacade;

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

            //Datos del formularios
            String nombres = request.getParameter("nombre");
            String apellidos = request.getParameter("apellido");
            String email = request.getParameter("email");
            String telefono = request.getParameter("telefono");
            String contrase??a = request.getParameter("contra");
            String tipo = request.getParameter("tipo");
            int identificacion = Integer.parseInt(request.getParameter("identificacion"));
            String fecha = request.getParameter("fecha de nacimiento");
            Integer municipio = Integer.parseInt(request.getParameter("municipio"));
            String direccion = request.getParameter("direccion");
            boolean presentaEnfermedad = (request.getParameter("check_enfermedad").equals("Si"))?true:false;
            boolean personalSalud = (request.getParameter("check_personal").equals("Si"))?true:false;
            String enfermedad = null;
            String personal = null;
            
            
            DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
            Date date= df.parse(fecha);
            int count=0;
           
            for (Usuario user : usuarioFacade.findAll()) {
                if (user.getIdentificacion() == identificacion) {
                    count=1;
                    break;
                }
            }
            if (count == 0) {
                if (!contrase??a.equals(request.getParameter("contraConfirmada"))) {
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"Contrase??as no coincidentes\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/registroUsuario.jsp\" />");
                } else {
                    if (presentaEnfermedad) {
                        enfermedad = request.getParameter("enfermedad");
                    }
                    if (!request.getParameter("profesion").equals("0")) {
                        personal = request.getParameter("profesion");
                    }
                    
                    Usuario usuario = new Usuario(identificacion, nombres, apellidos, date, telefono, email, contrase??a, tipo, direccion, municipioFacade.find(municipio), 
                                                  presentaEnfermedad, personalSalud, determinarFase(date, presentaEnfermedad, personalSalud,personal), enfermedad, personal);
                    if (request.getParameter("action").equals("Registrarse")) {
                        usuarioFacade.create(usuario);
                        out.println("<script type=\"text/javascript\">\n" + "  "
                                + "alert(\"Usuario registrado correctamente\");\n"
                                + "window.location.href =" + "\"http://localhost:8080/VAcovid-war/loginUsuario.jsp\"" +
                                "</script>");
                        
                    }
                }
            }else{
                    out.println("<script type=\"text/javascript\">\n" + "  alert(\"El usuario ya se encuentra registrado\");\n" + "</script>");
                    out.println("<meta http-equiv=\"refresh\" content=\"0; url=http://localhost:8080/VAcovid-war/registroUsuario.jsp\" />");
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>RegistroUsuario</title>");
            out.println("</head>");
            out.println("<body>");
           
            out.println("</html>");
        } catch (ParseException ex) {
            Logger.getLogger(RegistroUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int determinarFase(Date fecha, boolean presentaEnfermedad, boolean personalSalud, String personal)
    {
        LocalDate date= LocalDate.of(fecha.getYear()+1900, fecha.getMonth()+1, fecha.getDate());
        Period period = Period.between(date, LocalDate.now());
        
        if (period.getYears()>=80 || personalSalud)
        {
            return 1;
        }
        else if (period.getYears()>=60 && period.getYears()<80) 
        {
            return 2;
        }
        else if ((period.getYears()>=16 && period.getYears()<60 && presentaEnfermedad)|| (personal!=null && personal.equals("Personal educativo"))) {
            return 3;
        }
        else if (presentaEnfermedad) {
            return 4;
        }
        else if (period.getYears()>=16 && period.getYears()<60) {
            return 5;
        }
        else
        {
            return 0;
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
