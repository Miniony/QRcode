package com.example.akyllyoy;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatePicker vDatePicker = (DatePicker) findViewById(R.id.vDatePicker);

        final TimePicker vTimePicker = (TimePicker) findViewById(R.id.vTimePicker);
        Button btGetTime = (Button) findViewById(R.id.btGetTime);
        btGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: vDate: " + new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(DatePicker.getCalendar().getTime()));
                Log.e(TAG, "onClick vTime: " + new SimpleDateFormat("hh:mm:ss a", Locale.US).format(TimePicker.getCalendar().getTime()));
            }
        });

        Button btSetTime = (Button) findViewById(R.id.btSetTime);
        btSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = "14/01/2016 20:55";
                String format = "dd/MM/yyyy hh:mm";
                DatePicker.setDate(time, format);
                TimePicker.setTime(time, format);
            }
        });




    }

}

