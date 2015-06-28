package de.medienfreak.einkaufsliste;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView listenansicht;
    private EditText textEingabe;
    private Button addButton;
    private Context context = this;

    // private SQLiteDatabase database;
    // private MyDatabaseHelper dbHelper;
    private DatabaseManager dbManager;

    private List<String> liste = new ArrayList<String>();

    private void addContent() {
        dbManager.open(context);
        String text = textEingabe.getText().toString().trim();
        textEingabe.setText("");
        liste.add(text);
        dbManager.insertArtikel(liste.size(), text, 1);
        listenansicht.setAdapter(new ArrayAdapter<String>(context,
                R.layout.listeneintrag, liste));

        dbManager.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // this.database = dbHelper.getWritableDatabase();

        dbManager = new DatabaseManager();

        setContentView(R.layout.activity_main);
        this.listenansicht = (ListView) findViewById(R.id.list);

        this.textEingabe = (EditText) findViewById(R.id.article_input);
        this.textEingabe
                .setOnEditorActionListener(new OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        addContent();
                        textEingabe.clearFocus();
                        return false;
                    }
                });

        if (this.addButton == null) {
            this.addButton = (Button) findViewById(R.id.button_add);
            this.addButton.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    addContent();

                }

            });
        }
        dbManager.open(context);
        liste = dbManager.getArtikel();
        dbManager.close();
        listenansicht.setAdapter(new ArrayAdapter<String>(context,
                R.layout.listeneintrag, liste));

        listenansicht.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "LongClick ListItem Number " + position,
                        Toast.LENGTH_LONG).show();

                String text = liste.get(position);

                dbManager.open(context);
                dbManager.deleteArtikel(text);
                dbManager.close();
                liste.remove(position);
                listenansicht.setAdapter(new ArrayAdapter<String>(context,
                        R.layout.listeneintrag, liste));

                return false;
            }
        });
        // listenansicht.setOnItemClickListener(new OnItemClickListener() {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view,
        // int position, long id) {
        // Toast.makeText(getApplicationContext(),
        // "Click ListItem Number " + position, Toast.LENGTH_LONG)
        // .show();
        // }
        // });
    }

}
