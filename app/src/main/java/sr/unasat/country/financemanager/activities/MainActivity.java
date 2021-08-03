package sr.unasat.country.financemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sr.unasat.country.financemanager.R;
import sr.unasat.country.financemanager.dao.FinancialDAO;
import sr.unasat.country.financemanager.entities.User;

import static sr.unasat.country.financemanager.dao.FinancialDAO.USER_PASSWORD;
import static sr.unasat.country.financemanager.dao.FinancialDAO.USER_TABLE;
import static sr.unasat.country.financemanager.dao.FinancialDAO.USER_USERNAME;

public class MainActivity extends AppCompatActivity {

    private FinancialDAO financialDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        financialDAO = new FinancialDAO(this);
        //User user = financialDAO.findOneRecordByUsername("admin");

/*        List<ContentValues> records = new ArrayList<>();

      //insert record
        ContentValues record1 = new ContentValues();
        record1.put(USER_USERNAME, "spiderman2");
        record1.put(USER_PASSWORD, "pwd");
        records.add(record1);*/
      //  long result =  financialDAO.insertOneRecord(USER_TABLE, record1);
      //  user = financialDAO.findOneRecordByUsername("spiderman");
    /*    ContentValues record2 = new ContentValues();
        record2.put(USER_USERNAME, "superman2");
        record2.put(USER_PASSWORD, "pwd");
        records.add(record2);

        ContentValues record3 = new ContentValues();
        record3.put(USER_USERNAME, "wonderwoman2");
        record3.put(USER_PASSWORD, "pwd");
        records.add(record3);*/

       // financialDAO.insertMultipleRecord(USER_TABLE, records);

        ContentValues record = new ContentValues();
        record.put(USER_USERNAME, "Jesica");

        financialDAO.updateRecord(USER_TABLE, record, "wonderwoman2");

        financialDAO.deleteRecord(USER_TABLE, "spiderman2");



        TextView credentialsTextView = (TextView) findViewById(R.id.credentials);
        List<User> users = financialDAO.findAllRecords(USER_TABLE);
        StringBuilder credentialsText = new StringBuilder();
        for (User foundUser : users) {
            credentialsText.append(String.format("Username: %s \nPassword: %s",foundUser.getUserName(), foundUser.getPassword()));
            credentialsText.append("\n\n");
        }
        credentialsTextView.setText(credentialsText.toString());
    }


}