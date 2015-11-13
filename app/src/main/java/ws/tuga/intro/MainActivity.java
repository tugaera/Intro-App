package ws.tuga.intro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    //add the tag
    public static final String TAG = "HelloWorldActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Log de informacao");

        /*
        TextView tvInicio = new TextView(this);
        tvInicio.setText("A funcionar esta coisa...");
        setContentView(tvInicio);
        */

    }
    private TextView textout;
    public void buttonOnClick(View v) {
        //click do botão
        Button button= (Button) v;
        ((Button) v).setText("clicked");
        /*textout = (TextView) findViewById(R.id.textView1);
        textout.setText("Granda LOL"); */
        ((TextView) findViewById(R.id.textView1)).setText("Carregou Button");

        /*Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("mensagem", "Ola actividade secundaria!");
        startActivities(intent);
*/
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Uma mensagem de texto...");
        sendIntent.setType("text/plain");
        //startActivities(sendIntent);
    }

    public void onClickBtn(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, ((EditText) findViewById(R.id.EditText1)).getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void onClickBtnGoogle(View v)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.pt")));
    }

    private static final int REQUEST_CODE = 1;
    public void onClickBtnNomes(View v)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("firstName", ((EditText) findViewById(R.id.editText)).getText().toString());
        intent.putExtra("lastName", ((EditText) findViewById(R.id.editText2)).getText().toString());
        startActivityForResult(intent, REQUEST_CODE);
    }



    private static final int PICK_CONTACT_REQUEST = 2;
    public void onClickBtnContactos(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("name"))
                Toast.makeText(this, data.getExtras().getString("name"), Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK && requestCode == PICK_CONTACT_REQUEST) {
            Cursor cursor = getContentResolver().query(data.getData(), new String[]{ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name = cursor.getString(columnIndex);
                cursor.close();
                Toast.makeText(this, "Nome do contacto: " + name, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
