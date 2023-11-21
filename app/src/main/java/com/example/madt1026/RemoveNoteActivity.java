package com.example.madt1026;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.widget.Button;
import android.content.Intent;

public class RemoveNoteActivity extends AppCompatActivity {

    ListView lvRemoveNotes;
    ArrayAdapter<String> adapter;
    ArrayList<String> listNoteItems;
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_note);

        lvRemoveNotes = findViewById(R.id.lvRemoveNotes);
        btnGoBack = findViewById(R.id.btnGoBack);

        listNoteItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listNoteItems);

        lvRemoveNotes.setAdapter(adapter);

        SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());
        listNoteItems.addAll(savedSet);
        adapter.notifyDataSetChanged();

        lvRemoveNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeNoteAtPosition(position);
            }
        });



    btnGoBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){

            Intent intent = new Intent();
            intent.putStringArrayListExtra(Constants.UPDATED_NOTE_LIST, listNoteItems);
            setResult(RESULT_OK, intent);
            finish();


            /*SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
            Set<String> existingNotes = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>());

            // Remove deleted notes from the existing set
            existingNotes.removeAll(listNoteItems);

            // Update SharedPreferences with the modified set
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, existingNotes);
            editor.apply();

            // Pass the updated list back to MainActivity
            Intent intent = new Intent(RemoveNoteActivity.this, MainActivity.class);
            startActivity(intent);

            /*SharedPreferences sharedPref = getSharedPreferences(Constants.NOTES_FILE, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>(listNoteItems));
            editor.apply();
            Intent intent = new Intent(RemoveNoteActivity.this, MainActivity.class);
            //intent.putStringArrayListExtra(Constants.UPDATED_NOTE_LIST, listNoteItems);
            startActivity(intent);*/
    }
    });
}
    private void removeNoteAtPosition(int position) {
        String deletedNote = listNoteItems.get(position);
        listNoteItems.remove(position);
        adapter.notifyDataSetChanged();

        showToast("Note deleted: " + deletedNote);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
