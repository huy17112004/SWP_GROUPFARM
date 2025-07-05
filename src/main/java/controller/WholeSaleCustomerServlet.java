package controller;

import com.google.gson.Gson;
import dto.WholeSaleCustomerDTO;
import entity.WholesaleCustomer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WholesaleCustomerService;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/api/customer/save")
public class WholeSaleCustomerServlet extends HttpServlet {

    private final WholesaleCustomerService wholesaleCustomerSerivce = new WholesaleCustomerService();
    private final Gson gson = new Gson();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
