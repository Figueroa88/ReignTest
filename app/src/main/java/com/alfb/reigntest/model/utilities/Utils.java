package com.alfb.reigntest.model.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfb.reigntest.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @author Alejandro Figueroa
 * @version 1.0
 * @since 30-01-2019
 *
 * Clase que contiene las principales funciones de UTILIDAD en la APP
 */
public class Utils
{
    /////////////////////////////////////////////////
    ///                                           ///
    ///    LLAMADAS A ACTIVIDADES Y FRAGMENTOS    ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Llama para mostrar a la siguiente pantalla que herede de AppCompatActivity
     *
     * @param activityApp   AppCompatActivity donde se ubica
     * @param activityClass Class de la acividad a la cual se llamara
     */
    public static void callAppCompatActivity(Activity activityApp, Class activityClass)
    {
        try
        {
            //Cambio de animacion de la ventana
            activityApp.overridePendingTransition(R.anim.a_fade_in, R.anim.a_fade_out);

            Intent intentCall = new Intent(activityApp, activityClass);

            activityApp.setResult(Activity.RESULT_OK);
            activityApp.startActivityForResult(intentCall, 1);
            //El 1000 representa un numero o codigo cualquiera que puede ser usado en casa de que se necesite
            //hacer algo al momento de regresar a la ventana inicial en el StartResult
        }
        catch (Exception er)
        {
            Log.e(">>>>>>ERROR CALL ACT", er.toString());
        }
    }

    /**
     * Llama para mostrar a la siguiente pantalla que herede de AppCompatActivity
     *
     * @param activityApp   AppCompatActivity donde se ubica
     * @param activityClass Class de la acividad a la cual se llamara
     * @param flags         Int con los FLAGS a enviar en el intent
     */
    public static void callAppCompatActivity(Activity activityApp, Class activityClass, int flags)
    {
        try
        {
            //Cambio de animacion de la ventana
            activityApp.overridePendingTransition(R.anim.a_fade_in, R.anim.a_fade_out);

            Intent intentCall = new Intent(activityApp, activityClass);
            intentCall.setFlags(flags);

            activityApp.setResult(Activity.RESULT_OK);
            activityApp.startActivityForResult(intentCall, 1);
            //El 1 representa un numero o codigo cualquiera que puede ser usado en casa de que se necesite
            //hacer algo al momento de regresar a la ventana inicial en el StartResult
        }
        catch (Exception er)
        {
            Log.e(">>>>>>ERROR CALL ACT", er.toString());
        }
    }

    /////////////////////////////////////////////////
    ///                                           ///
    ///       VALIDACION DE INTERNET EN APP       ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Verifica si el dispositivo tiene conexion a Internet
     *
     * @param compatActivity AppCompatActivity donde se ubica
     *
     * @return TRUE o FALSE
     */
    public static boolean isHaveInternetConnection(final Context compatActivity)
    {
        boolean valueResponse;

        try
        {
            ConnectivityManager connectionManager = (ConnectivityManager) compatActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = connectionManager.getActiveNetworkInfo();

            valueResponse = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        catch (Exception e)
        {
            valueResponse = false;
        }

        return valueResponse;
    }

    /////////////////////////////////////////////////
    ///                                           ///
    ///       FECHAS Y FORMATOS DE NUMERO         ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Asigna el formato de puntos y coma a un numero
     *
     * @param numberFormatter Int al cual se le asignara el formato
     *
     * @return String con el numero del formato
     */
    public static String assignNumberFormat(int numberFormatter)
    {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.GERMAN);

        return String.valueOf(numberFormat.format(numberFormatter));
    }

    /**
     * Cambia el formato de fecha de un String por otro formato de fecha
     *
     * @param valueChange String con el valor que se desea cambair el formato
     * @param oldFormat   String con el formato en que esta la fecha actualmente
     * @param newFormat   String con el nuevo formato con el que se desea este la fecha
     *
     * @return String
     */
    public static String changeDateFormat(String valueChange, String oldFormat, String newFormat)
    {
        String valueReturn;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldFormat);

        try
        {
            Date oldDate = simpleDateFormat.parse(valueChange);

            simpleDateFormat.applyPattern(newFormat);

            valueReturn = simpleDateFormat.format(oldDate);
        }
        catch (Exception e)
        {
            valueReturn = valueChange;
        }

        return valueReturn;
    }

    /**
     * Retorna la fecha actual en el formato indicado
     *
     * @param dateFormat String con el formato en el cual se devolvera la fecha
     *
     * @return String
     */
    public static String getCurrentDate(String dateFormat)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat;

        //Formato por defecto
        if (dateFormat == null)
        {
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        }
        else
        {
            simpleDateFormat = new SimpleDateFormat(dateFormat);
        }

        return simpleDateFormat.format(calendar.getTime());
    }

    /////////////////////////////////////////////////
    ///                                           ///
    ///         VALIDACIONES DE STRINGS           ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Valida si un String esta vacio
     *
     * @param text String a validar
     *
     * @return TRUE si esta vacio y FALSE en caso contrario
     */
    public static boolean isEmpty(String text)
    {
        return (text.replace(" ", "").length() == 0);
    }

    /**
     * Vaida si en un grupo de Strings hay algun elemento vacio
     *
     * @param groupString String... grupo de elementos a evaludar
     *
     * @return True si hay alguno vacio y FALSE en caso que no haya ninguno vacio
     */
    public static boolean isEmpty(String... groupString)
    {
        boolean validateReturn = false;

        int sizeEvalue = groupString.length;

        for (int i = 0; i < sizeEvalue; i++)
        {
            if (groupString[i].replace(" ", "").equals(""))
            {
                validateReturn = true;

                break;
            }
        }

        return validateReturn;
    }


    /////////////////////////////////////////////////
    ///                                           ///
    ///       VERIFICACION DE PERMISOS            ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Realiza la peticion de un permiso en especifico para la aplicacion cuando este no esta activo
     *
     * @param compatActivity AppCompatActivity donde se ubica
     * @param permissionName String con el nombre del permiso
     * @param codePermission Int con el codigo del permiso a utilizar para verificar el mismo
     *
     * @return TRUE si el permiso esta activo y FALSE si no
     */
    public static boolean requestAppPermission(final AppCompatActivity compatActivity, final String permissionName,
                                               int codePermission)
    {
        boolean validatePermission = true;

        if (ActivityCompat.checkSelfPermission(compatActivity, permissionName) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(compatActivity, new String[]{permissionName}, codePermission);

            validatePermission = false;
        }

        return validatePermission;
    }

    /**
     * Realiza la peticion de un permiso en especifico para la aplicacion cuando este no esta activo
     *
     * @param compatActivity AppCompatActivity donde se ubica
     * @param permissionName String[] con el nombre de los permisos a solicitar
     * @param codePermission Int con el codigo del permiso a utilizar para verificar el mismo
     *
     * @return TRUE si el permiso esta activo y FALSE si no
     */
    public static boolean requestAppPermission(final AppCompatActivity compatActivity, final String[] permissionName,
                                               int codePermission)
    {
        boolean validatePermission = true;
        int sizePermissions = permissionName.length;

        for (int i = 0; i < sizePermissions; i++)
        {
            if (ActivityCompat.checkSelfPermission(compatActivity, permissionName[i]) != PackageManager.PERMISSION_GRANTED)
            {
                validatePermission = false;

                break;
            }
        }

        //Entramos si se consiguio almenos un permiso que no esta activo
        if (!validatePermission)
        {
            //Se vuelve a solicitar el o los permisos que fueron rechazados

            ActivityCompat.requestPermissions(compatActivity, permissionName, codePermission);
        }

        return validatePermission;
    }

    /**
     * Realiza la peticion de un permiso en especifico para la aplicacion cuando este no esta activo
     *
     * @param compatActivity Activity donde se ubica el fragmento
     * @param permissionName String con el nombre del permiso
     * @param codePermission Int con el codigo del permiso a utilizar para verificar el mismo
     *
     * @return TRUE si el permiso esta activo y FALSE si no
     */
    public static boolean requestAppPermission(final Activity compatActivity, final String permissionName,
                                               int codePermission)
    {
        boolean validatePermission = true;

        if (ActivityCompat.checkSelfPermission(compatActivity, permissionName) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(compatActivity, new String[]{permissionName}, codePermission);

            validatePermission = false;
        }

        return validatePermission;
    }

    /**
     * Realiza la peticion de un permiso en especifico para la aplicacion cuando este no esta activo
     *
     * @param currentFragment Fragment donde se ubica el fragmento
     * @param permissionName  String con el nombre del permiso
     * @param codePermission  Int con el codigo del permiso a utilizar para verificar el mismo
     *
     * @return TRUE si el permiso esta activo y FALSE si no
     */
    public static boolean requestAppPermission(final Fragment currentFragment, final String permissionName,
                                               int codePermission)
    {
        boolean validatePermission = true;

        if (ActivityCompat.checkSelfPermission(currentFragment.getContext(), permissionName) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(currentFragment.getActivity(), new String[]{permissionName}, codePermission);

            validatePermission = false;
        }

        return validatePermission;
    }

    /**
     * Realiza la peticion de un permiso en especifico para la aplicacion cuando este no esta activo
     *
     * @param compatActivity Activity donde se ubica el fragmento
     * @param permissionName String[] con el nombre de los permisos a solicitar
     * @param codePermission Int con el codigo del permiso a utilizar para verificar el mismo
     *
     * @return TRUE si el permiso esta activo y FALSE si no
     */
    public static boolean requestAppPermission(final Activity compatActivity, final String[] permissionName,
                                               int codePermission)
    {
        boolean validatePermission = true;
        int sizePermissions = permissionName.length;

        for (int i = 0; i < sizePermissions; i++)
        {
            if (ActivityCompat.checkSelfPermission(compatActivity, permissionName[i]) != PackageManager.PERMISSION_GRANTED)
            {
                validatePermission = false;

                break;
            }
        }

        //Entramos si se consiguio almenos un permiso que no esta activo
        if (!validatePermission)
        {
            //Se vuelve a solicitar el o los permisos que fueron rechazados

            ActivityCompat.requestPermissions(compatActivity, permissionName, codePermission);
        }

        return validatePermission;
    }

    /////////////////////////////////////////////////
    ///                                           ///
    ///        SHOW DIALOG ALERT AND EXIT         ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Muestra un mensaje de SALIDA en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     */
    public static void showDialogExit(final AppCompatActivity currentActivity)
    {
        //DialogExit dialogAlert = new DialogExit(currentActivity);
        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion (Se configura solamente el mensaje)
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param messageDialog   String mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final String messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion (Se configura solamente el mensaje)
     *
     * @param currentContext Context donde se ubica
     * @param messageDialog  String mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final Context currentContext, final String messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentContext);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param messageDialog   @StringRes Int mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final @StringRes int messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity, "", false, false);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param titleDialog     String con el titulo de la alerta
     * @param messageDialog   String mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final String titleDialog, final String messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity, "", false, false);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.setDialogTitle(titleDialog);

        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param titleDialog     String con el titulo de la alerta
     * @param messageDialog   @StringRes int mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final String titleDialog, final @StringRes int messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity, "", false, false);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.setDialogTitle(titleDialog);

        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param titleDialog     @StringRes Int con el titulo de la alerta
     * @param messageDialog   @StringRes Int mensaje en el alerta a mostrar
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final @StringRes int titleDialog, final @StringRes int messageDialog)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity, "", false, false);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.setDialogTitle(titleDialog);

        //dialogAlert.show();
    }

    /**
     * Muestra un mensaje de alerta en la aplicacion
     *
     * @param currentActivity AppCompatActivity donde se ubica
     * @param messageDialog   @StringRes Int mensaje en el alerta a mostrar
     * @param exitApp         Boolean indicando si se desea salir de la app
     */
    public static void showDialogAlert(final AppCompatActivity currentActivity, final @StringRes int messageDialog, boolean exitApp)
    {
        //DialogAlert dialogAlert = new DialogAlert(currentActivity, "", false, false);

        //dialogAlert.setDialogMessage(messageDialog);
        //dialogAlert.setExitApp(exitApp);
        //dialogAlert.show();
    }

    /////////////////////////////////////////////////
    ///                                           ///
    ///         CARGA DE IMAGENES POR URL         ///
    ///                                           ///
    /////////////////////////////////////////////////

    /**
     * Carga una IMAGEN ubicada en un URL a través de una libreria EXTERNA llamado PICASSO
     *
     * @param imageURL       String con la direccion URL de la imagen
     * @param imgPhoto       ImageView donde se quiere colocar la imagen cargada
     * @param lblPhoto       TextView que presenta la imagen de muestra para mostrar o esconder
     * @param currentContext Context donde se ubica actualmente
     * @param rounded        Boolean que indica si se hara el redondeo o no de la foto
     */
    public static void loadPhotoFromURL(String imageURL, final ImageView imgPhoto, final TextView lblPhoto,
                                        final Context currentContext, final boolean rounded)
    {
        if (imageURL != null && !Utils.isEmpty(imageURL))
        {
            Picasso.get().load(imageURL).fit().centerCrop().into(imgPhoto, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    Bitmap bitmapPhoto = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();

                    if (rounded)
                    {
                        //imgPhoto.setImageDrawable(ImageManager.returnRoundedImage(bitmapPhoto, currentContext));
                    }
                    else
                    {
                        imgPhoto.setImageBitmap(bitmapPhoto);
                    }

                    imgPhoto.setVisibility(ImageView.VISIBLE);
                    lblPhoto.setVisibility(TextView.GONE);
                }

                @Override
                public void onError(Exception e)
                {
                    lblPhoto.setVisibility(TextView.VISIBLE);
                }
            });
        }
    }
}
