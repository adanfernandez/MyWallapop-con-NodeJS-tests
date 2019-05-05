
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
		// Vemos que accede a su vista 
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
		// Volvemos a la página de inicio de sesión
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
	
	
	//ADMINISTRADOR: ELIMINACIÓN DE USUARIOS
	
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
	
	// PR12.   Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
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
	
	
	// PR13.    Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza y dicho usuario desaparece.
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
	
	// PR14.	Ir al formulario de alta de oferta, rellenarla con datos válidos y pulsar el botón Submit. 
	//				Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void PR14() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.clickOption(driver, "/productos/agregar", "class", "btn btn-primary");
		PO_AddProduct.fillForm(driver, "Lámpara", "Flexo de estudio excelente para esas interminables noches haciendo SDI!", "15.00");
		// Nos redirecciona a nuestras compras. Está alli
		PO_HomeView.checkElement(driver, "text", "Lámpara");
	}
	
	// PR15.	Ir al formulario de alta de oferta, rellenarla con datos inválidos (campo título vacío) y pulsar
	//			el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio
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
		// En la BBDD inicial tiene definidas un ladrón y unas gafas
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
	
	// PR18.	  Ir a la lista de ofertas, borrar la última oferta de la lista, comprobar que la lista se actualiza y que la oferta desaparece 
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
	
	// PR19.	Hacer una búsqueda con el campo vacío y comprobar que se muestra la página que corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void PR19() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(6, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay 5 productos, habrá 6 hr.
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
		elementos.get(1).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(4, elementos.size());
	}
	
	// PR20.	Hacer una búsqueda escribiendo en el campo un texto que no exista y comprobar que se muestra la página que corresponde, con la lista de ofertas vacía.
	@Test
	public void PR20() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "cocodrilo escocés");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(1, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay 0 productos, habrá un hr.
	}
	
	
	// PR21.	Hacer una búsqueda escribiendo en el campo un texto en minúscula o mayúscula y comprobar que se muestra la página que corresponde, 
	//				con la lista de ofertas que contengan dicho texto,
	//				independientemente que el título esté almacenado en minúsculas o mayúscula
	@Test
	public void PR21() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");		
		PO_SearchProducts.fillForm(driver, "LaMpArA");
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//hr", PO_View.getTimeout());
		assertEquals(2, elementos.size()); //Hay un hr por cada producto, y uno al final. Si hay un producto, habrá 2 hr.
	}
	
	
	
	
	// COMPRAR OFERTA
	
	// PR22.	Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja un saldo positivo en el contador del comprobador. 
	//				Y comprobar que el contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR22() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 €");
		PO_ListView.clickOption(driver, "comprar/lampara", "@id", "desconectarse");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 88 €");
	}
	
	// PR23.	Sobre una búsqueda determinada (a elección de desarrollador), comprar una oferta que deja un saldo 0 en el contador del comprobador. 
	//				Y comprobar que el contador se actualiza correctamente en la vista del comprador.
	@Test
	public void PR23() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 €");
		PO_ListView.clickOption(driver, "comprar/ordenador", "@id", "desconectarse");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 0 €");
	}
	
	// PR24.	Sobre una búsqueda determinada (a elección de desarrollador), intentar comprar una oferta que esté por encima de saldo disponible del comprador. 
	//				Y comprobar que se muestra el mensaje de saldo no suficiente
	@Test
	public void PR24() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 €");
		PO_ListView.clickOption(driver, "comprar/nokia", "@id", "comprar/nokia");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 €");
		PO_HomeView.checkElement(driver, "text", "No posee suficiente dinero");
	}
	
	
	
	// VER EL LISTADODE OFERTAS COMPRADAS
	// PR25.	Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen las ofertas que deben aparecer
	@Test
	public void PR25() {
		driver.navigate().to(URL + "/testing");
		PO_HomeView.clickOption(driver, "identificarse", "id", "identificacion");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Dinero disponible: 100 €");
		PO_ListView.clickOption(driver, "comprar/lampara", "@id", "desconectarse");
		//Ya en las compras comprobamos que está la recién comprada
		PO_HomeView.checkElement(driver, "text", "lampara");
	}
	
	
	
	
	// AUTENTIFICACIÓN DE USUARIO
	
	// PR29.	Inicio de sesión con datos válidos.
	@Test
	public void PR29()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "123456");
		PO_HomeView.checkElement(driver, "text", "Nombre");
		PO_HomeView.checkElement(driver, "text", "Descripción");
		PO_HomeView.checkElement(driver, "text", "Precio");
		PO_HomeView.checkElement(driver, "text", "Propietario");
	}
	
	// PR30.	 Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta).
	@Test
	public void PR30()
	{
		driver.navigate().to(URL + "/testing");
		driver.navigate().to(URL + "/cliente.html");
		PO_LoginView.fillForm(driver, "adalino@adalino", "654321");
		PO_HomeView.checkElement(driver, "text", "Introduzca los campos correctamente");
	}
	
	// PR31.	Inicio de sesión con datos válidos (campo email o contraseña vacíos).
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
		//Comprobamos que no están las ofertas de Adalino
		PO_HomeView.checkNoElement(driver, "ladron");
		PO_HomeView.checkNoElement(driver, "gafas");
		//Comprobamos que están el resto
		PO_HomeView.checkElement(driver, "text", "nokia");
		PO_HomeView.checkElement(driver, "text", "lampara");
		PO_HomeView.checkElement(driver, "text", "llavero");
		//Comprobamos que no se muestran los productos ya vendidos
		PO_HomeView.checkNoElement(driver, "radio");
		PO_HomeView.checkNoElement(driver, "gafas");
		PO_HomeView.checkNoElement(driver, "balon");

	}
	
	
	
	// ENVÍO Y VISUALIZACIÓN DE MENSAJES
	
	// PR33.	Sobre una búsqueda determinada de ofertas (a elección de desarrollador), enviar un mensaje a
	//				una oferta concreta. Se abriría dicha conversación por primera vez. Comprobar que el mensaje aparece
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
		
	// PR34.	Sobre el listado de conversaciones enviar un mensaje a una conversación ya abierta.
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
		elemento.sendKeys("¿Podría darme más información acerca del producto?");
		driver.findElement(By.className("btn")).click();
		PO_HomeView.checkElement(driver, "text", "¿Podría darme más información acerca del producto?");
		elemento = driver.findElement(By.name("mensaje"));
		elemento.click();
		elemento.clear();
		elemento.sendKeys("Espero la respuesta");
		driver.findElement(By.className("btn")).click();
		PO_HomeView.checkElement(driver, "text", "Espero la respuesta");
	}
	
	
}
