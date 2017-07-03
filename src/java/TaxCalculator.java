/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for tax calculation
 * @author katharina
 */
public class TaxCalculator extends HttpServlet {

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
            float amount = Float.parseFloat(request.getParameter("amount"));
            float tax = Float.parseFloat(request.getParameter("tax"));
            String currency = request.getParameter("currency");
            double res = calculateTax(amount,tax);
            
            writeLogFile(amount,tax,res,currency);
            
            out.println("Total amount: "+res+" "+currency);
        }
    }
    
    /**
     * Part 2) Calculates the total amount
     * @param amount    The net amount
     * @param tax       The tax in percentage
     * @return The total amount
     */
    private Double calculateTax(float amount, float tax){
        double res =  amount * ((tax+100)/100);
        // round 2 digits
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(res));
        
    }

    /**
     * Part 3) Write into a file
     * @param amount    The net amount
     * @param tax       The tax in percentage
     * @param currency  The currency
     */
    private void writeLogFile(float amount, float tax, double tamount, String currency){
        
        File file = new File("taxcalc.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(TaxCalculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            
            Date date = new Date(); 
            String entry ="Timestamp: "+date.toString()+" - Amount: "+amount+" - Tax: "+tax+" - Total: "+tamount+" - Currency: "+currency;
            br.append(entry);
            
            br.close();
            
        } catch (IOException ex) {
            Logger.getLogger(TaxCalculator.class.getName()).log(Level.SEVERE, null, ex);
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
