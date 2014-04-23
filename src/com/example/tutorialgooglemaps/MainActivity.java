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
	 * Comenzamos declarando dos objetos: UPV hacer referencia a la posición geográfica
	 *  de la Universidad Politécnica de Valencia y mapa, que nos permitirá acceder al objeto
	 *   GoogleMapque hemos insertado en un fragment de nuestro Layout. 
	 *   Este objeto es inicializado al comienzo de onCreate() y a continuación 
	 *   se utilizan una serie de métodos para configurarlo.setMapType() 
	 *   permite seleccionar el tipo de mapa (normal, satélite, hibrido o relieve).
	 *    Para averiguar las constantes correspondientes te recomendamos que utilices 
	 *    la opción de autocompletar (escribe GoogleMap. y podrás seleccionar las
	 *     constantes de esta clase). El método moveCamera() desplaza el área
	 *      de visualización a una determinada posición (UPV) a la vez que define el nivel
	 *       de zoom  (15). El nivel de zoom ha de estar en un rango de 2 (continente)
	 *        hasta 21 (calle). El método setMyLocationEnabled(true) activa la visualización 
	 *        de la posición del dispositivo por medio del típico triangulo azul. 
	 *        El método getUiSettings() permite configurar las acciones del interfaz de usuario.
	 *         En este ejemplo se han utilizado dos, desactivar los botones de zoom 
	 *         y visualizar una brújula. Puedes usar autocompletar para descubrir 
	 *         otras posibles configuraciones. El método addMarker() permite añadir 
	 *         los típicos marcadores que habrás visto en muchos mapas.ejemplo 
	 *         se indica como posición UPV, un título, una descripción, como icono 
	 *         se utiliza el mismo drawable usado como icono de la aplicación y el punto
	 *          del icono que haremos coincidir con el punto exacto que queremos indicar en 
	 *          el mapa. Un valor de (0, 0) corresponde a la esquina superior izquierda del icono 
	 *          y (1, 1) a la esquina inferior derecha. Como nuestro icono tiene forma de círculo
	 *           , hemos indicado el valor (0.5, 0.5) para que coincida con su centro.
	 *            Finalmente, hemos registrado un escuchador de evento para detectar 
	 *            pulsaciones sobre la pantalla. El escuchador vamos a ser nosotros mismos (this),
	 *             por lo que hemos implementado el interfaceOnMapClickListener 
	 *             y añadido el método onMapClick().
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
				.snippet("Universidad Politécnica de Valencia")
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
	 * desplaza el punto de visualización a la UPV. 
	 * A diferencia del uso anterior, sin cambiar el nivel de zoom
	 * que el usuario tenga seleccionado.
	 * 
	 */
	
	public void moveCamera(View view) {
		mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
	}

	/*
	 * nos desplaza hasta nuestra posición actual por medio de una animación 
	 * (similar a la que a veces utilizan en el Tele Diario para mostrar un punto en conflicto).
	 * 
	 * 
	 * getMyLocation() permite obtener la posición del dispositivo sin usar el API Android 
	 * de posicionamiento. Si usas este método verifica siempre que ya se dispone 
	 * de una posición (!= null) y que has pedido permisos de localización.
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
	 * añade un nuevo marcador en el centro del mapa que estamos observando
	 * (getCameraPosition()). En este caso usaremos el marcador por defecto, sin
	 * información adicional.
	 * 
	 * Como hemos indicado onMapClick() será llamado cuando se pulse sobre el
	 * mapa. Se pasa como parámetro las coordenadas donde se ha pulsado, que
	 * utilizaremos para añadir un marcador. Esta vez el marcador por defecto,
	 * es de color amarillo.
	 */
	
	
	public void addMarker(View view) {
		mapa.addMarker(new MarkerOptions().position(new LatLng(mapa
				.getCameraPosition().target.latitude,
				mapa.getCameraPosition().target.longitude)));

	}



}
