package com.example.tutorialgooglemaps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		OnMapClickListener {
	
	/*
	 * 
	 * Comenzamos declarando dos objetos: UPV hacer referencia a la posici�n geogr�fica
	 *  de la Universidad Polit�cnica de Valencia y mapa, que nos permitir� acceder al objeto
	 *   GoogleMapque hemos insertado en un fragment de nuestro Layout. 
	 *   Este objeto es inicializado al comienzo de onCreate() y a continuaci�n 
	 *   se utilizan una serie de m�todos para configurarlo.setMapType() 
	 *   permite seleccionar el tipo de mapa (normal, sat�lite, hibrido o relieve).
	 *    Para averiguar las constantes correspondientes te recomendamos que utilices 
	 *    la opci�n de autocompletar (escribe GoogleMap. y podr�s seleccionar las
	 *     constantes de esta clase). El m�todo moveCamera() desplaza el �rea
	 *      de visualizaci�n a una determinada posici�n (UPV) a la vez que define el nivel
	 *       de zoom  (15). El nivel de zoom ha de estar en un rango de 2 (continente)
	 *        hasta 21 (calle). El m�todo setMyLocationEnabled(true) activa la visualizaci�n 
	 *        de la posici�n del dispositivo por medio del t�pico triangulo azul. 
	 *        El m�todo getUiSettings() permite configurar las acciones del interfaz de usuario.
	 *         En este ejemplo se han utilizado dos, desactivar los botones de zoom 
	 *         y visualizar una br�jula. Puedes usar autocompletar para descubrir 
	 *         otras posibles configuraciones. El m�todo addMarker() permite a�adir 
	 *         los t�picos marcadores que habr�s visto en muchos mapas.ejemplo 
	 *         se indica como posici�n UPV, un t�tulo, una descripci�n, como icono 
	 *         se utiliza el mismo drawable usado como icono de la aplicaci�n y el punto
	 *          del icono que haremos coincidir con el punto exacto que queremos indicar en 
	 *          el mapa. Un valor de (0, 0) corresponde a la esquina superior izquierda del icono 
	 *          y (1, 1) a la esquina inferior derecha. Como nuestro icono tiene forma de c�rculo
	 *           , hemos indicado el valor (0.5, 0.5) para que coincida con su centro.
	 *            Finalmente, hemos registrado un escuchador de evento para detectar 
	 *            pulsaciones sobre la pantalla. El escuchador vamos a ser nosotros mismos (this),
	 *             por lo que hemos implementado el interfaceOnMapClickListener 
	 *             y a�adido el m�todo onMapClick().
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
	private final LatLng UPV = new LatLng(39.481106, -0.340987);
	private GoogleMap mapa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 15));
		mapa.setMyLocationEnabled(true);
		mapa.getUiSettings().setZoomControlsEnabled(false);
		mapa.getUiSettings().setCompassEnabled(true);
		mapa.addMarker(new MarkerOptions()
				.position(UPV)
				.title("UPV")
				.snippet("Universidad Polit�cnica de Valencia")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_launcher))
				.anchor(0.5f, 0.5f));
		mapa.setOnMapClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onMapClick(LatLng puntoPulsado) {
		mapa.addMarker(new MarkerOptions().position(puntoPulsado).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

	}

	/*
	 * 
	 * desplaza el punto de visualizaci�n a la UPV. 
	 * A diferencia del uso anterior, sin cambiar el nivel de zoom
	 * que el usuario tenga seleccionado.
	 * 
	 */
	
	public void moveCamera(View view) {
		mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
	}

	/*
	 * nos desplaza hasta nuestra posici�n actual por medio de una animaci�n 
	 * (similar a la que a veces utilizan en el Tele Diario para mostrar un punto en conflicto).
	 * 
	 * 
	 * getMyLocation() permite obtener la posici�n del dispositivo sin usar el API Android 
	 * de posicionamiento. Si usas este m�todo verifica siempre que ya se dispone 
	 * de una posici�n (!= null) y que has pedido permisos de localizaci�n.
	 * 
	 * */
	
	
	public void animateCamera(View view) {
		if (mapa.getMyLocation() != null)
			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					mapa.getMyLocation().getLatitude(), mapa.getMyLocation()
							.getLongitude()), 15));
	}

	/*
	 * 
	 * a�ade un nuevo marcador en el centro del mapa que estamos observando
	 * (getCameraPosition()). En este caso usaremos el marcador por defecto, sin
	 * informaci�n adicional.
	 * 
	 * Como hemos indicado onMapClick() ser� llamado cuando se pulse sobre el
	 * mapa. Se pasa como par�metro las coordenadas donde se ha pulsado, que
	 * utilizaremos para a�adir un marcador. Esta vez el marcador por defecto,
	 * es de color amarillo.
	 */
	
	
	public void addMarker(View view) {
		mapa.addMarker(new MarkerOptions().position(new LatLng(mapa
				.getCameraPosition().target.latitude,
				mapa.getCameraPosition().target.longitude)));

	}



}
