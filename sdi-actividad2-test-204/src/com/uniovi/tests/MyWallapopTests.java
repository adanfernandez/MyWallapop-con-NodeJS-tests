
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
	static String Geckdriver024 = "C:\\Users\\adanv\\Desktop\\EII\\SDI\\Laboratorio\\5\\PL-SDI-Sesi�n5-material\\pl5\\geckodriver022win64.exe";
	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8081";

	

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	
	// PR01. Registro de Usuario con datos v�lidos
	@Test
	public void PR01() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "pepe@suarez", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Identificaci�n de usuario");
	}

	// PR02. Registro de usuario introduciendo mal la contrase�a
	@Test
	public void PR02() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "pepe@suarez", "Josefo", "Perez", "123456", "654321");
		PO_View.checkElement(driver, "text", "Las contrase�as deben coincidir");
	}

	// PR03. Registro de Usuario con email existente
	@Test
	public void PR03() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "registrarse", "id", "registrarse");
		PO_RegisterView.fillForm(driver, "adalino@adalino", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "text", "Usuario ya existente");
	}

	
	
	
	//INICIO DE SESI�N
	
	// PR04. Inicio de sesi�n con datos v�lidos.
	@Test
	public void PR04() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_View.checkElement(driver, "text", "Productos");
	}
		
	// PR05. Inicio de sesi�n con datos inv�lidos (email existente, pero contrase�a incorrecta).
	@Test
	public void PR05() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "654321");
		PO_View.checkElement(driver, "text", "Email o password incorrecto");
	}
	
	
	// PR06. Inicio de sesi�n con datos v�lidos (campo email o contrase�a vac�os).
	@Test
	public void PR06() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "");
		//Al ser un campo required, no pasar� nada.
		PO_View.checkElement(driver, "text", "No puede haber campos vac�os");
	}

	// PR07. Inicio de sesi�n con datos inv�lidos (email no existente en la aplicaci�n).
	@Test
	public void PR07() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "UO251162@adalino", "123456");
		//Al ser un campo required, no pasar� nada.
		PO_View.checkElement(driver, "text", "Email o password incorrecto");
	}

	
	
	//FIN DE SESI�N
	
	// PR08. Hacer click en la opci�n de salir de sesi�n y comprobar que se redirige a la p�gina de inicio de sesi�n (Login)
	@Test
	public void PR08() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_View.checkElement(driver, "text", "Productos");
		PO_HomeView.clickOption(driver, "desconectarse", "id", "identificarse");
		PO_View.checkElement(driver, "text", "Identificaci�n de usuario");
	}
	
	// PR09. Comprobar que el bot�n cerrar sesi�n no est� visible si el usuario no est� autenticado.
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
	
	
	
	
	//ADMINISTRADOR: LISTADO
	
	// PR10. Mostrar el listado de usuarios y comprobar que se muestran todos los que existen en el sistema
	@Test
	public void PR10() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 4);
	}
	
	
	//ADMINISTRADOR: ELIMINACI�N DE USUARIOS
	
	// PR11.  Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
	@Test
	public void PR11() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		List<WebElement> elementos = driver.findElements(By.name("usuarios"));
		elementos.get(0).click();
		PO_ListView.clickOption(driver, "buttonDelete", "@id", "buttonDelete");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
	}
	
	// PR12.   Ir a la lista de usuarios, borrar el �ltimo usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
	@Test
	public void PR12() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		List<WebElement> elementos = driver.findElements(By.name("usuarios"));
		elementos.get(3).click();
		PO_ListView.clickOption(driver, "buttonDelete", "@id", "buttonDelete");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 3);
	}
	
	
	// PR13.    Ir a la lista de usuarios, borrar el �ltimo usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
	@Test
	public void PR13() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		List<WebElement> elementos = driver.findElements(By.name("usuarios"));
		elementos.get(0).click();
		elementos.get(1).click();
		elementos.get(2).click();
		PO_ListView.clickOption(driver, "buttonDelete", "@id", "buttonDelete");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}
	
	
	
	// DAR DE ALTA UNA OFERTA
	
	// PR14.	Ir al formulario de alta de oferta, rellenarla con datos v�lidos y pulsar el bot�n Submit. 
	//				Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void PR14() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.clickOption(driver, "/productos/agregar", "class", "btn btn-primary");
		PO_AddProduct.fillForm(driver, "L�mpara", "Flexo de estudio excelente para esas interminables noches haciendo SDI!", "15.00");
		PO_HomeView.checkElement(driver, "text", "L�mpara");
	}
	
	// PR15.	Ir al formulario de alta de oferta, rellenarla con datos inv�lidos (campo t�tulo vac�o) y pulsar
	//			el bot�n Submit. Comprobar que se muestra el mensaje de campo obligatorio
	@Test
	public void PR15() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.clickOption(driver, "/productos/agregar", "class", "btn btn-primary");
		PO_AddProduct.fillForm(driver, "", "Flexo de estudio excelente para esas interminables noches haciendo SDI!", "15.00");
		PO_HomeView.checkElement(driver, "text", "Inserte los campos obligatorios");
	}
	
	
	
	
	// OFERTAS PROPIAS
	// PR16.	 Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que existen para este usuario. 
	@Test
	public void PR16() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.clickOption(driver, "/publicaciones", "class", "btn btn-info");
		PO_HomeView.checkElement(driver, "text", "ladron");
		PO_HomeView.checkElement(driver, "text", "gafas de sol");
	}
}
