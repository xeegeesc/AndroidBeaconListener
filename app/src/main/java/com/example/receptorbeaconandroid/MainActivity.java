package com.example.receptorbeaconandroid;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    private static final String ETIQUETA_LOG = ">>>>";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private Intent elIntentDelServicio = null;
    private BluetoothLeScanner mBluetoothLeScanner;
    private BluetoothAdapter mBluetoothAdapter;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void botonArrancarServicioPulsado( View v ) {
        Log.d(ETIQUETA_LOG, " boton arrancar servicio Pulsado");

        if (this.elIntentDelServicio != null) {
            // ya estaba arrancado
            return;
        }

        //si los permisos no estan concedidos, los solicita
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            solicitarPermisos();
        }

        //utiliza el adaptador bluetooth del telefono
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

/*
        setScanFilter();
        setScanSettings();
        mBluetoothLeScanner.startScan(Arrays.asList(mScanFilter), mScanSettings, mScanCallback);
*/
        Log.d(ETIQUETA_LOG, " MainActivity.constructor : voy a arrancar el servicio");

        this.elIntentDelServicio = new Intent(this, ServicioEscuharBeacons.class);

        this.elIntentDelServicio.putExtra("tiempoDeEspera", (long) 5000);
        startService(this.elIntentDelServicio);

    } // ()

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public void botonDetenerServicioPulsado( View v ) {

        if ( this.elIntentDelServicio == null ) {
            // no estaba arrancado
            return;
        }

        stopService( this.elIntentDelServicio );

        this.elIntentDelServicio = null;

        Log.d(ETIQUETA_LOG, " boton detener servicio Pulsado" );


    } // ()

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(ETIQUETA_LOG, " MainActivity.constructor : empieza");



        Log.d(ETIQUETA_LOG, " MainActivity.constructor : acaba");

    }


//------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------
    //PERMISOS
//------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------
    private void solicitarPermisos(){
        //Solicitar permiso bluetooth para detectar beacons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//version minima Android M
            //chequea permisos de Android M
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Esta APP necesita permisos de localización.");
                builder.setMessage("Porfavor, otorgue los permisos para poder detectar beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }
    //Una vez obtenido el resultado de la peticion de los permisos informamos del estado, si se han permitido o no.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISOBLUETOOTH", "Localización permitida");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Funcionalidad limitada");
                    builder.setMessage("Como el acceso a la localización no ha sido otorgado, esta app no podra detectar beacons en segundo plano.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
    }//OnRequestPermission
} // class
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------------------