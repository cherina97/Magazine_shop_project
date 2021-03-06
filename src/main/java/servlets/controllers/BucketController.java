package servlets.controllers;

import com.google.gson.Gson;
import dtos.BucketProductDto;
import entities.Bucket;
import entities.Product;
import services.BucketService;
import services.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet("/api/buckets")
public class BucketController extends HttpServlet {
    private BucketService bucketService = BucketService.getInstance();
    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");
        int userId = (int) req.getSession().getAttribute("userId");

        bucketService.create(new Bucket.Builder()
                .withUserId(userId)
                .withProductId(Integer.parseInt(productId))
                .withDate(new Date())
                .build());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int userId = (int) req.getSession().getAttribute("userId");

        List<Bucket> buckets = bucketService.getAllByUserId(userId);

        Set<Integer> productIds = buckets.stream()
                .map(Bucket::getProduct_id)
                .collect(Collectors.toSet());

        Map<Integer, Product> productsGroupedById = productService.readAllByIds(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<BucketProductDto> result = buckets.stream()
                .map(bucket -> {
                    BucketProductDto dto = new BucketProductDto();
                    dto.id = bucket.getId();
                    dto.purchase_date = bucket.getPurchase_date();
                    int productId = bucket.getProduct_id();

                    dto.product = productsGroupedById.get(productId);
                    return dto;
                })
                .collect(Collectors.toList());

        String json = new Gson().toJson(result);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int bucketId = Integer.parseInt(req.getParameter("bucketId"));
        int userId = (int) req.getSession().getAttribute("userId");
        Optional<Bucket> bucketIdRead = bucketService.read(bucketId);

        if(bucketIdRead.get().getUser_id() == userId){
            bucketService.delete(bucketId);
        } else {
            resp.getWriter().write("Error! It's not your bucket!");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}


