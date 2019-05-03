
package com.uniovi.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.uniovi.tests.pageobjects.PO_AddProduct;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_ListView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_SearchProducts;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class MyWallapopTests {
	

	static String PathFirefox65 = "C:\\Users\\adanv\\Desktop\\FirefoxPortable\\App\\Firefox64\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\adanv\\Desktop\\EII\\SDI\\Laboratorio\\5\\PL-SDI-Sesión5-material\\pl5\\geckodriver022win64.exe";
	// ComÃºn a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8081";

	

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciÃ³nn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// DespuÃ©s de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la Ãºltima prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	
	// PR01. Registro de Usuario con datos válidos
	@Test
	public void PR01() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "pepe@suarez", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}

	// PR02. Registro de usuario introduciendo mal la contraseña
	@Test
	public void PR02() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "pepe@suarez", "Josefo", "Perez", "123456", "654321");
		PO_View.checkElement(driver, "text", "Las contraseñas deben coincidir");
	}

	// PR03. Registro de Usuario con email existente
	@Test
	public void PR03() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "adalino@adalino", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Usuario ya existente");
	}

	
	
	
	//INICIO DE SESIÓN
	
	// PR04. Inicio de sesión con datos válidos.
	@Test
	public void PR04() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_View.checkElement(driver, "text", "Productos");
	}
		
	// PR05. Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta).
	@Test
	public void PR05() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "654321");
		PO_View.checkElement(driver, "text", "Email o password incorrecto");
	}
	
	
	// PR06. Inicio de sesión con datos válidos (campo email o contraseña vacíos).
	@Test
	public void PR06() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "");
		//Al ser un campo required, no pasará nada.
		PO_View.checkElement(driver, "text", "No puede haber campos vacíos");
	}

	// PR07. Inicio de sesión con datos inválidos (email no existente en la aplicación).
	@Test
	public void PR07() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "UO251162@adalino", "123456");
		//Al ser un campo required, no pasará nada.
		PO_View.checkElement(driver, "text", "Email o password incorrecto");
	}

	
	
	//FIN DE SESIÓN
	
	// PR08. Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión (Login)
	@Test
	public void PR08() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_View.checkElement(driver, "text", "Productos");
		PO_HomeView.clickOption(driver, "desconectarse", "id", "identificarse");
		PO_View.checkElement(driver, "text", "Identificación de usuario");
	}
	
	// PR09. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
	@Test
	public void PR09() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.checkNoElement(driver, "desconectarse");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_View.checkElement(driver, "text", "Productos");
		PO_HomeView.clickOption(driver, "desconectarse", "id", "identificarse");
		PO_HomeView.checkNoElement(driver, "desconectarse");
	}
	
	
	
	
}
