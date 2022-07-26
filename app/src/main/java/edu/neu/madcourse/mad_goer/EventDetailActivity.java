package edu.neu.madcourse.mad_goer;


import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import edu.neu.madcourse.mad_goer.messages.Event;


public class EventDetailActivity extends AppCompatActivity {
    private Event event;
    private Button joinBtn;
    private Button cancelBtn;
    private TextView hostTV;
    private TextView eventNameTV;
    private TextView timeTV;
    private TextView categoryTV;
    private TextView isPublicTV;
    private TextView isVirtualTV;
    private TextView addressTV;
    private TextView attendingListTV;
    private TextView descriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        TextView scrollGoers = (TextView) findViewById(R.id.id_goers_detail);
        scrollGoers.setMovementMethod(new ScrollingMovementMethod());
        TextView scrollDesc = (TextView) findViewById(R.id.id_desc_detail);
        scrollDesc.setMovementMethod(new ScrollingMovementMethod());


        joinBtn = (Button) findViewById(R.id.btn_join_detail);
        cancelBtn = (Button) findViewById(R.id.btn_cancel_detail);
        hostTV = (TextView) findViewById(R.id.txt_host_detail);
        eventNameTV = (TextView) findViewById(R.id.id_event_name_detail);
        timeTV = (TextView) findViewById(R.id.id_timestamp_detail);
        categoryTV = (TextView) findViewById(R.id.txt_categories_detail);
        isPublicTV = (TextView) findViewById(R.id.txt_isPublic_detail);
        isVirtualTV = (TextView) findViewById(R.id.txt_location_detail);
        addressTV = (TextView) findViewById(R.id.id_location_detail);
        attendingListTV = (TextView) findViewById(R.id.id_goers_detail);
        descriptionTV = (TextView) findViewById(R.id.id_desc_detail);


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:
                //check if event attendingList is full
                //if not full: Toast "Congratulations! Successfully Join!
                //if full: Toast "Sorry, this event is full!"

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:
                //return to main activity
            }
        });

        hostTV.setText(event.getHost().toString());
        eventNameTV.setText(event.getEventName());
        timeTV.setText(event.getStartDate().toString());
        categoryTV.setText(event.getCategory().toString());
        isPublicTV.setText(checkPublic(event));
        isVirtualTV.setText(checkVirtual(event));
        addressTV.setText(location(event));
        attendingListTV.setText(event.getAttendingList().toString());

    }

    //public tag text
    public String checkPublic(Event event){
        String check;
        if(this.event.isPublic()){
            check = "Public";
        }else{
            check = "Private";
        }
        return check;
    }

    public String checkVirtual(Event event){
        String method;
        if(this.event.isInPerson()){
            method = "Address: ";
        }else{
            method = "Link: ";
        }
        return method;
    }

    //location bar content
    public String location(Event event){
        String address;
        if(this.event.isInPerson()){
//            method = "Address: ";
            address = event.getLocation().toString();
        }else{
//            method = "Link: ";
            address = event.getLink();
        }
        return address;
    }

}
