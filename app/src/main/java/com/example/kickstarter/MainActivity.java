package com.example.kickstarter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    String Url,Title,imageurl,By,Pledged,Funded,Time,Backers,Country,State,Currency,Blurb;
    ImageView imageView,back;
    TextView click,TitleName,ByName,UrlName,LocationName,TimeName,BackersName,PledgedName,FundedName,BlurbName;
    CardView Loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.image);
        back = (ImageView)findViewById(R.id.back);
        click = (TextView)findViewById(R.id.clickme);
        TitleName = (TextView)findViewById(R.id.title);
        ByName = (TextView)findViewById(R.id.ByName);
        UrlName = (TextView)findViewById(R.id.title);
        LocationName = (TextView)findViewById(R.id.LocationName);
        TimeName = (TextView)findViewById(R.id.TimeName);
        BackersName = (TextView)findViewById(R.id.BackersName);
        PledgedName = (TextView)findViewById(R.id.PledgedName);
        FundedName = (TextView)findViewById(R.id.FundedName);
        BlurbName = (TextView)findViewById(R.id.BlurbName);
        Loc = (CardView)findViewById(R.id.location);
        Url = getIntent().getExtras().getString("Url");
        Blurb = getIntent().getExtras().getString("Blurb");
        Title = getIntent().getExtras().getString("title");
        By = getIntent().getExtras().getString("By");
        Country = getIntent().getExtras().getString("Country");
        Currency = getIntent().getExtras().getString("Currency");
        State = getIntent().getExtras().getString("State");
        Time = getIntent().getExtras().getString("Time");
        Backers = getIntent().getExtras().getString("Backers");
        Pledged = getIntent().getExtras().getString("Pledged");
        Toast.makeText(this,Pledged,Toast.LENGTH_LONG).show();
        Funded = getIntent().getExtras().getString("Funded");
        TitleName.setText(Title);
        ByName.setText(By);
        LocationName.setText(State+","+Country);
        FundedName.setText(Funded);
        TimeName.setText(Time);
        BackersName.setText(Backers);
        if(Currency.equals("usd")){
            PledgedName.setText("$"+Pledged);
        }
        if (Currency.equals("cad")){
            PledgedName.setText("C$"+Pledged);
        }
        if(Currency.equals("eur")){
            PledgedName.setText("€"+Pledged);
        }
        if (Currency.equals("gbp")){
            PledgedName.setText("£"+Pledged);
        }
        if(Currency.equals("aud")){
            PledgedName.setText("A$"+Pledged);
        }
        if (Currency.equals("chf")){
            PledgedName.setText("CHf"+Pledged);
        }
        if(Currency.equals("dkk")){
            PledgedName.setText("Kr."+Pledged);
        }
        FundedName.setText(Funded);
        BlurbName.setText(Blurb);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.kickstarter.com"+Url));
                startActivity(intent);
            }
        });
        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://www.google.com/maps/search/?api=1&query="+ State + "," +Country;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        DatabaseReference url = FirebaseDatabase.getInstance().getReference("/Image/");
        url.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageurl = dataSnapshot.getValue().toString();
                Picasso.with(getApplicationContext())
                        .load(imageurl)
                        .fit()
                        .into(imageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
