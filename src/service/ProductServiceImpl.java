package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ProductDAO;
import model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDAO;
	
	@Override
	public List<Product> getAllProducts() {
		return productDAO.getAllProducts();
	}

	@Override
	public Product getProductById(String productId) {
		return productDAO.getProductById(productId);
	}

	@Override
	public List<Product> getProductsByCategory(String categoryName) {
		return productDAO.getProductsByCategory(categoryName);
	}

	@Override
	public List<Product> getProductsByBrands(List<String> brands) {
		return productDAO.getProductsByBrands(brands);
	}

	@Override
	public void addProduct(Product product) {
		productDAO.addProduct(product);
	}

}
