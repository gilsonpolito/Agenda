package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;


public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private ContatoDAO cDAO;
    private NumberPicker npMes;
    private NumberPicker npDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        npDia = (NumberPicker) findViewById(R.id.np_dia);
        npDia.setMinValue(1);
        npDia.setMaxValue(31);

        npMes = (NumberPicker) findViewById(R.id.np_mes);
        npMes.setMinValue(1);
        npMes.setMaxValue(12);
        npMes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int anterior, int novo) {
                setDiaToView(novo);
            }
        });

        if (getIntent().hasExtra("contato")) {
            this.c = (Contato) getIntent().getSerializableExtra("contato");
            EditText nameText = (EditText) findViewById(R.id.editTextNome);
            nameText.setText(c.getNome());
            EditText foneText = (EditText) findViewById(R.id.editTextFone);
            foneText.setText(c.getFone());
            EditText emailText = (EditText) findViewById(R.id.editTextEmail);
            emailText.setText(c.getEmail());
            EditText foneComercialText = (EditText) findViewById(R.id.editTextFoneComercial);
            foneComercialText.setText(c.getFoneComercial());
            int pos = c.getNome().indexOf(" ");
            if (pos == -1)
                pos = c.getNome().length();
            setTitle(c.getNome().substring(0, pos));
            if (c.getMesAniversario() != 0) {
                npMes.setValue(c.getMesAniversario());
                setDiaToView(c.getMesAniversario());
                npDia.setValue(c.getDiaAniversario());
            }
        }
        cDAO = new ContatoDAO(this);
    }

    private void setDiaToView(int mes) {
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                npDia.setMaxValue(31);
                break;
            case 2:
                npDia.setMaxValue(29);
                break;
            default:
                npDia.setMaxValue(30);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar() {
        cDAO.apagaContato(c);
        Intent resultIntent = new Intent();
        setResult(3, resultIntent);
        finish();
    }

    private void salvar() {
        String name = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String foneComercial = ((EditText) findViewById(R.id.editTextFoneComercial)).getText().toString();
        int mes = npMes.getValue();
        int dia = npDia.getValue();

        if (c == null)
            c = new Contato();

        c.setNome(name);
        c.setFone(fone);
        c.setEmail(email);
        c.setFoneComercial(foneComercial);
        c.setMesAniversario(mes);
        c.setDiaAniversario(dia);

        cDAO.salvaContato(c);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}

