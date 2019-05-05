
package com.uniovi.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
		// Vemos que accede a su vista 
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
		// Volvemos a la p�gina de inicio de sesi�n
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
		// 4 usuarios
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
		// Eliminamos un usuario. Quedan 3
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
		// Eliminamos un usuario. Quedan 3
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
		// Eliminamos 3 usuarios. Queda 1
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
		// Nos redirecciona a nuestras compras. Est� alli
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
		// En la BBDD inicial tiene definidas un ladr�n y unas gafas
		PO_HomeView.checkElement(driver, "text", "ladron");
		PO_HomeView.checkElement(driver, "text", "gafas");
	}
	
	
	
	
	//DAR DE BAJA UNA OFERTA
	
	// PR17.	 Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y que la oferta desaparece. 
	@Test
	public void PR17() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_HomeView.clickOption(driver, "/publicaciones", "class", "btn btn-info");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
		PO_ListView.clickOption(driver, "eliminar/ladron", "@id", "eliminar/gafas");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}
	
	// PR18.	  Ir a la lista de ofertas, borrar la �ltima oferta de la lista, comprobar que la lista se actualiza y que la oferta desaparece 
	@Test
	public void PR18() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_HomeView.clickOption(driver, "/publicaciones", "class", "btn btn-info");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 2);
		PO_ListView.clickOption(driver, "eliminar/gafas", "@id", "eliminar/ladron");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
	}
	
	
	
	
	
	// BUSCAR OFERTAS
	
	// PR19.	Hacer una b�squeda con el campo vac�o y comprobar que se muestra la p�gina que corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR19() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(6, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay 5 productos, habr� 6 hr.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(1).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(4, elementos.size());
	}
	
	// PR20.	Hacer una b�squeda escribiendo en el campo un texto que no exista y comprobar que se muestra la p�gina que corresponde, con la lista de ofertas vac�a.
	@Test
	public void PR20() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "cocodrilo escoc�s");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(1, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay 0 productos, habr� un hr.
	}
	
	
	// PR21.	Hacer una b�squeda escribiendo en el campo un texto en min�scula o may�scula y comprobar que se muestra la p�gina que corresponde, 
	//				con la lista de ofertas que contengan dicho texto,
	//				independientemente que el t�tulo est� almacenado en min�sculas o may�scula
	@Test
	public void PR21() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "LaMpArA");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(2, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay un producto, habr� 2 hr.
	}
	
	
	
	
	// COMPRAR OFERTA
	
	// PR22.	Sobre una b�squeda determinada (a elecci�n de desarrollador), comprar una oferta que deja un saldo positivo en el contador del comprobador. 
	//				Y comprobar que el contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR22() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 �");
		PO_ListView.clickOption(driver, "comprar/lampara", "@id", "desconectarse");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 88 �");
	}
	
	// PR23.	Sobre una b�squeda determinada (a elecci�n de desarrollador), comprar una oferta que deja un saldo 0 en el contador del comprobador. 
	//				Y comprobar que el contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR23() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 �");
		PO_ListView.clickOption(driver, "comprar/ordenador", "@id", "desconectarse");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 0 �");
	}
	
	// PR24.	Sobre una b�squeda determinada (a elecci�n de desarrollador), intentar comprar una oferta que est� por encima de saldo disponible del comprador. 
	//				Y comprobar que se muestra el mensaje de saldo no suficiente
	@Test
	public void PR24() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 �");
		PO_ListView.clickOption(driver, "comprar/nokia", "@id", "comprar/nokia");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 �");
		PO_HomeView.checkElement(driver, "text", "No posee suficiente dinero");
	}
	
	
	
	// VER EL LISTADODE OFERTAS COMPRADAS
	// PR25.	Ir a la opci�n de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen las ofertas que deben aparecer
	@Test
	public void PR25() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 �");
		PO_ListView.clickOption(driver, "comprar/lampara", "@id", "desconectarse");
		//Ya en las compras comprobamos que est� la reci�n comprada
		PO_HomeView.checkElement(driver, "text", "lampara");
	}
	
	
	
	
	// AUTENTIFICACI�N DE USUARIO
	
	// PR29.	Inicio de sesi�n con datos v�lidos.
	@Test
	public void PR29()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Nombre");
		PO_HomeView.checkElement(driver, "text", "Descripci�n");
		PO_HomeView.checkElement(driver, "text", "Precio");
		PO_HomeView.checkElement(driver, "text", "Propietario");
	}
	
	// PR30.	 Inicio de sesi�n con datos inv�lidos (email existente, pero contrase�a incorrecta).
	@Test
	public void PR30()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "654321");
		PO_HomeView.checkElement(driver, "text", "Introduzca los campos correctamente");
	}
	
	// PR31.	Inicio de sesi�n con datos v�lidos (campo email o contrase�a vac�os).
	@Test
	public void PR31()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "");
		PO_HomeView.checkElement(driver, "text", "Introduzca los campos correctamente");
	}
	
	
	
	// MOSTRADO DE OFERTAS DISPONIBLES
	
	// PR32.	Mostrar el listado de ofertas disponibles y comprobar que se muestran todas las que existen, menos las del usuario identificado.
	@Test
	public void PR32()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		//Comprobamos que no est�n las ofertas de Adalino
		PO_HomeView.checkNoElement(driver, "ladron");
		PO_HomeView.checkNoElement(driver, "gafas");
		//Comprobamos que est�n el resto
		PO_HomeView.checkElement(driver, "text", "nokia");
		PO_HomeView.checkElement(driver, "text", "lampara");
		PO_HomeView.checkElement(driver, "text", "llavero");
		//Comprobamos que no se muestran los productos ya vendidos
		PO_HomeView.checkNoElement(driver, "radio");
		PO_HomeView.checkNoElement(driver, "gafas");
		PO_HomeView.checkNoElement(driver, "balon");

	}
	
	
	
	// ENV�O Y VISUALIZACI�N DE MENSAJES
	
	// PR33.	Sobre una b�squeda determinada de ofertas (a elecci�n de desarrollador), enviar un mensaje a
	//				una oferta concreta. Se abrir�a dicha conversaci�n por primera vez. Comprobar que el mensaje aparece
	//				en el listado de mensajes.
	@Test
	public void PR33()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", "Chat", PO_View.getTimeout());
		elementos.get(2).click();
		WebElement elemento = driver.findElement(By.name("mensaje"));
		elemento.click();
		elemento.clear();
		elemento.sendKeys("Hola, estoy interesado en su producto");
		driver.findElement(By.className("btn")).click();
		PO_HomeView.checkElement(driver, "text", "Hola, estoy interesado en su producto");
	}
		
	// PR34.	Sobre el listado de conversaciones enviar un mensaje a una conversaci�n ya abierta.
	//				Comprobar que el mensaje aparece en el listado de mensajes.
	@Test
	public void PR34()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "text", "Chat", PO_View.getTimeout());
		elementos.get(3).click();
		PO_HomeView.checkElement(driver, "text", "Hola. Estoy interesado");
		WebElement elemento = driver.findElement(By.name("mensaje"));
		elemento.click();
		elemento.clear();
		elemento.sendKeys("�Podr�a darme m�s informaci�n acerca del producto?");
		driver.findElement(By.className("btn")).click();
		PO_HomeView.checkElement(driver, "text", "�Podr�a darme m�s informaci�n acerca del producto?");
		elemento = driver.findElement(By.name("mensaje"));
		elemento.click();
		elemento.clear();
		elemento.sendKeys("Espero la respuesta");
		driver.findElement(By.className("btn")).click();
		PO_HomeView.checkElement(driver, "text", "Espero la respuesta");
	}
	
	
}
