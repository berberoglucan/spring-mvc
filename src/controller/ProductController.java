package controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.Product;
import service.ProductService;
import validator.ProductValidator;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	// validation support
	@Autowired
	private ProductValidator productValidator;

	@RequestMapping
	public String defaultPage(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "productList";
	}

	@RequestMapping("/all")
	public String allProducts(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "productList";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam(name = "id") String productId, Model model) {
		Product product = productService.getProductById(productId);
		model.addAttribute("product", product);
		return "productDetail";
	}

	@RequestMapping("/category/{categoryName}")
	public String getProductsByCategoryName(@PathVariable(name = "categoryName") String categoryName, Model model) {

		List<Product> productByCategory = productService.getProductsByCategory(categoryName);

		model.addAttribute("products", productByCategory);

		return "productList";
	}

	@RequestMapping("/brand/{brands}")
	public String getProductsByBrands(@MatrixVariable(pathVar = "brands") List<String> brands, Model model) {

		List<Product> productsByBrands = productService.getProductsByBrands(brands);

		model.addAttribute("products", productsByBrands);

		return "productList";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	// jsp sayfasindaki modelAttribute ile ayni isimde olmalidir. Yoksa exception firlatir.
	public String addProductPage(@ModelAttribute("newProduct") Product theNewProduct) {
		System.out.println("addProductPage#get invoked!!");
		
		// initialize model
		theNewProduct.setDescription("test test");
		theNewProduct.setCategory("TV");
		// return "productAdd";
		// return "productAdd_messageBundle";
		return "productAdd_messageBundle_validation";
	}
	
	// 1
	/* @RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPageSubmit(@ModelAttribute("newProduct") Product theNewProduct) {
		productService.addProduct(theNewProduct);
		return "redirect:/products";
	}*/
	
	// 2
	/* @RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPageSubmit(@ModelAttribute("newProduct") Product theNewProduct, BindingResult bindingResult) {
		
		productValidator.validate(theNewProduct, bindingResult);
		
		if(bindingResult.hasErrors()) {
			return "productAdd_messageBundle_validation";
		}
		
		productService.addProduct(theNewProduct);
		return "redirect:/products";
	}*/
	
	// 3
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPageSubmit(@ModelAttribute("newProduct") @Valid Product theNewProduct, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "productAdd_messageBundle_validation";
		}
		
		productService.addProduct(theNewProduct);
		return "redirect:/products";
	}
	
	

	// <form:select id="manufacturer" path="manufacturer" type="text"
	// class="form:input-large" items="${manufacturerList}"/>
	// ${manufacturerList}
	@ModelAttribute("manufacturerList")
	public List<String> prepareManufacturers() {
		System.out.println("prepareManufacturers invoked!!");
		return Arrays.asList("Apple", "Samsung", "Dell", "Google");
	}

	// <form:select id="category" path="category" type="text"
	// class="form:input-large" items="${categoryList}"/>
	// ${categoryList}
	@ModelAttribute("categoryList")
	public List<String> prepareCategories() {
		System.out.println("prepareCategories invoked!!");
		return Arrays.asList("Tablet", "Laptop", "TV", "Smart Phone");
	}

	// <form:radiobuttons path="condition" items="${conditionsMap}"/>
	// ${conditionsMap}
	@ModelAttribute("conditionsMap")
	public Map<String, String> prepareConditions() {
		System.out.println("prepareConditions invoked!!");
		Map<String, String> conditions = new LinkedHashMap<>();

		conditions.put("new", "New");
		conditions.put("old", "Old");
		conditions.put("refurbished", "Refurbished");

		return conditions;

	}

}
