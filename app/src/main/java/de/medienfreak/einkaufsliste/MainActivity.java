package de.medienfreak.einkaufsliste;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String[] ARTICLES = new String[] {"Butter","Brot","Nudeln","Nudelsauce","Nüsse"};

    private ListView listenansicht;
    private AutoCompleteTextView textEingabe;
    private Button addButton;
    private Context context = this;

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

        dbManager = new DatabaseManager();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ARTICLES);

        setContentView(R.layout.activity_main);
        this.listenansicht = (ListView) findViewById(R.id.list);

        this.textEingabe = (AutoCompleteTextView) findViewById(R.id.article_input);

        this.textEingabe.setMaxLines(1);
        this.textEingabe.setAdapter(adapter);

        this.textEingabe
                .setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
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

                String text = liste.get(position);

                Toast.makeText(getApplicationContext(),
                        text+" wird gelöscht.",
                        Toast.LENGTH_LONG).show();

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
