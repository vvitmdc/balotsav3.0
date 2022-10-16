package app.balotsav.com.vvitbalotsav.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import app.balotsav.com.vvitbalotsav.model.Event;
import app.balotsav.com.vvitbalotsav.model.Schools;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "Balotsav";
    private static final int VERSION = 1;


    public static final String SCHOOL_TABLE = "myTable";
    public static final String SCHOOL_CODE = "schoolCode";
    public static final String SCHOOL_NAME = "schoolName";
    public static final String SCHOOL_BOARD = "schoolBoard";
    public static final String SCHOOL_PASSWORD = "schoolPassword";
    public static final String SCHOOL_HEADMASTER_NAME = "schoolHeadMasterName";
    public static final String SCHOOL_HEADMASTER_EMAIL = "schoolHeadMasterEmail";
    public static final String SCHOOL_HEADMASTER_PHONE = "schoolHeadMasterPhone";
    public static final String SCHOOL_COORDINATOR_NAME = "schoolCoordinatorName";
    public static final String SCHOOL_COORDINATOR_PHONE = "schoolCoordinatorPhone";
    public static final String SCHOOL_TOWN = "schoolTown";
    public static final String SCHOOL_DISTRICT = "schoolDistrict";
    public static final String SCHOOL_STATE = "schoolState";
    public static final String SCHOOL_PINCODE = "schoolPincode";
    public static final String SCHOOL_SUBJUNIOIRS = "schoolSubjuniors";
    public static final String SCHOOL_JUNIORS = "schoolJuniors";
    public static final String SCHOOL_SENIORS = "schoolSeniors";
    public static final String SCHOOL_TOTAL = "schoolsTotal";
    public static final String SCHOOL_PARTICIPANTS = "schoolParticipants";
    public static final String SCHOOL_BOYS = "schoolParticipantsBoys";
    public static final String SCHOOL_GIRLS = "schoolParticipantsGirls";
    public static final String SCHOOL_HOSTEL_PARTICIPANTS = "schoolParticipantsHostel";
    public static final String SCHOOL_HOSTEL_BOYS = "schoolParticipantsBoysHostel";
    public static final String SCHOOL_HOSTEL_GIRLS = "schoolParticipantsGirlsHostel";
    public static final String SCHOOL_HOSTEL = "schoolHostel";
    public static final String EVENT_TABLE = "eventTable";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_JUNIORS = "eventJuniors";
    public static final String EVENT_SUBJUNIORS = "eventSubJuniors";
    public static final String EVENT_SENOIRS = "eventSeniors";
    public static final String EVENT_MAX = "eventMax";
    public static final String EVENT_TEAM = "eventTeam";
    public static final String EVENT_REGISTERED = "eventRegistered";
    public static final String SCHOOL_HOSTEL_DATE1 = "schoolDate1";
    public static final String SCHOOL_HOSTEL_DATE2 = "schoolDate2";
    public static final String SCHOOL_HOSTEL_DATE3 = "schoolDate3";
    private static final String SCHOOL_BOARDING_PLACE = "schoolBoardingPlace";
    private static final String SCHOOL_ADDRESS = "schoolAddress";



    public DbHelper(Context context) {
        super(context, DBNAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "
                + SCHOOL_TABLE + " ( "
                + SCHOOL_CODE + " TEXT, "
                + SCHOOL_NAME + " TEXT, "
                + SCHOOL_BOARD + " TEXT, "
                + SCHOOL_PASSWORD + " TEXT, "
                + SCHOOL_HEADMASTER_NAME + " TEXT, "
                + SCHOOL_HEADMASTER_EMAIL + " TEXT, "
                + SCHOOL_HEADMASTER_PHONE + " TEXT, "
                + SCHOOL_COORDINATOR_NAME + " TEXT, "
                + SCHOOL_COORDINATOR_PHONE + " TEXT, "
                + SCHOOL_ADDRESS + " TEXT, "
                + SCHOOL_TOWN + " TEXT, "
                + SCHOOL_DISTRICT + " TEXT, "
                + SCHOOL_STATE + " TEXT, "
                + SCHOOL_PINCODE + " TEXT, "
                + SCHOOL_PARTICIPANTS + " TEXT, "
                + SCHOOL_BOYS + " TEXT, "
                + SCHOOL_GIRLS + " TEXT, "
                + SCHOOL_HOSTEL + " TEXT, "
                + SCHOOL_HOSTEL_PARTICIPANTS + " TEXT, "
                + SCHOOL_HOSTEL_BOYS + " TEXT, "
                + SCHOOL_HOSTEL_GIRLS + " TEXT, "
                + SCHOOL_SUBJUNIOIRS + " TEXT, "
                + SCHOOL_JUNIORS + " TEXT, "
                + SCHOOL_SENIORS + " TEXT, "
                + SCHOOL_HOSTEL_DATE1 + " TEXT, "
                + SCHOOL_HOSTEL_DATE2 + " TEXT, "
                + SCHOOL_HOSTEL_DATE3 + " TEXT, "
                + SCHOOL_BOARDING_PLACE + " TEXT, "
                + SCHOOL_TOTAL + " TEXT ) ";

        String query2 = "CREATE TABLE "
                + EVENT_TABLE + " ( "
                + EVENT_NAME + " TEXT, "
                + EVENT_SUBJUNIORS + " INTEGER, "
                + EVENT_JUNIORS + " INTEGER, "
                + EVENT_SENOIRS + " INTEGER, "
                + EVENT_MAX + " INTEGER, "
                + EVENT_TEAM + " INTEGER, "
                + EVENT_REGISTERED + " INTEGER )";

        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addEvent(Event myEvent) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EVENT_NAME, myEvent.getName());
        contentValues.put(EVENT_SUBJUNIORS, myEvent.getSj());
        contentValues.put(EVENT_JUNIORS, myEvent.getJ());
        contentValues.put(EVENT_SENOIRS, myEvent.getS());
        contentValues.put(EVENT_MAX, myEvent.getMax());
        contentValues.put(EVENT_TEAM, myEvent.getTeam());
        contentValues.put(EVENT_REGISTERED, myEvent.isRegistered());

        long i = db.insert(EVENT_TABLE, null, contentValues);
        return i != -1;
    }

    public void updateEvent(Event myEvent, String eventName) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EVENT_NAME, myEvent.getName());
        contentValues.put(EVENT_SUBJUNIORS, myEvent.getSj());
        contentValues.put(EVENT_JUNIORS, myEvent.getJ());
        contentValues.put(EVENT_SENOIRS, myEvent.getS());
        contentValues.put(EVENT_MAX, myEvent.getMax());
        contentValues.put(EVENT_TEAM, myEvent.getTeam());
        contentValues.put(EVENT_REGISTERED, myEvent.isRegistered());
        db.update(EVENT_TABLE, contentValues, "eventName = ?", new String[]{eventName});

    }

    public Integer deleteEvent(String eventName) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(EVENT_TABLE, "eventName = ?", new String[]{eventName});
    }

    public Cursor getEventList() {

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + EVENT_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> myList = new ArrayList<>();
        Event event;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + EVENT_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        try {
           // cursor.moveToFirst();
            while (cursor.moveToNext()) {
                event = new Event();
                event.setName(cursor.getString(0));
                event.setSj(cursor.getInt(1));
                event.setJ(cursor.getInt(2));
                event.setS(cursor.getInt(3));
                event.setMax(cursor.getInt(4));
                event.setTeam(cursor.getInt(5));
                if (cursor.getInt(6) == 1)
                    event.setRegistered(true);
                else
                    event.setRegistered(false);
                myList.add(event);
                Log.i("Test",event.getName()+myList.size());

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
        return myList;
    }

    public Event getEvent(Event e) {
        Event event = null;
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + EVENT_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        try {
            //cursor.moveToFirst();
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equals(e.getName())) {
                    event = new Event();
                    event.setName(cursor.getString(0));
                    event.setSj(cursor.getInt(1));
                    event.setJ(cursor.getInt(2));
                    event.setS(cursor.getInt(3));
                    event.setMax(cursor.getInt(4));
                    event.setTeam(cursor.getInt(5));
                    if (cursor.getInt(6) == 1)
                        event.setRegistered(true);
                    else
                        event.setRegistered(false);
                    break;
                }


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
        return event;
    }

    public Integer deleteAllEvents() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(EVENT_TABLE, null, null);
    }

    public boolean setSchool(Schools mySchool) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOL_CODE, mySchool.getSchool_Code());
        contentValues.put(SCHOOL_NAME, mySchool.getSchool_Name());
        contentValues.put(SCHOOL_BOARD, mySchool.getBoard());
        contentValues.put(SCHOOL_PASSWORD, mySchool.getPassword());
        contentValues.put(SCHOOL_HEADMASTER_NAME, mySchool.getHeadMaster_Name());
        contentValues.put(SCHOOL_HEADMASTER_EMAIL, mySchool.getHeadMaster_Email());
        contentValues.put(SCHOOL_HEADMASTER_PHONE, mySchool.getHeadMaster_PhNo());
        contentValues.put(SCHOOL_COORDINATOR_NAME, mySchool.getCoordinator_Name());
        contentValues.put(SCHOOL_COORDINATOR_PHONE, mySchool.getCoordinator_PhNo());
        contentValues.put(SCHOOL_TOWN, mySchool.getTown());
        contentValues.put(SCHOOL_ADDRESS,mySchool.getAddress());
        contentValues.put(SCHOOL_DISTRICT, mySchool.getDistrict());
        contentValues.put(SCHOOL_STATE, mySchool.getState());
        contentValues.put(SCHOOL_PINCODE, mySchool.getPinCode());
        contentValues.put(SCHOOL_SUBJUNIOIRS, mySchool.getSj());
        contentValues.put(SCHOOL_JUNIORS, mySchool.getJ());
        contentValues.put(SCHOOL_SENIORS, mySchool.getS());
        contentValues.put(SCHOOL_TOTAL, mySchool.getTotal());
        contentValues.put(SCHOOL_HOSTEL,mySchool.getHostel());
        contentValues.put(SCHOOL_PARTICIPANTS, mySchool.getStudentCount());
        contentValues.put(SCHOOL_BOYS, mySchool.getBoysCount());
        contentValues.put(SCHOOL_GIRLS, mySchool.getGirlsCount());
        contentValues.put(SCHOOL_HOSTEL_PARTICIPANTS, mySchool.getStudentHostelCount());
        contentValues.put(SCHOOL_HOSTEL_BOYS, mySchool.getBoysHostelCount());
        contentValues.put(SCHOOL_HOSTEL_GIRLS, mySchool.getGirlsHostelCount());
        contentValues.put(SCHOOL_HOSTEL_DATE1,mySchool.getDate1());
        contentValues.put(SCHOOL_HOSTEL_DATE2,mySchool.getDate2());
        contentValues.put(SCHOOL_HOSTEL_DATE3,mySchool.getDate3());
        contentValues.put(SCHOOL_BOARDING_PLACE,mySchool.getBoardingPlace());

        long i = db.insert(SCHOOL_TABLE, null, contentValues);
        return i != -1;


    }


    public void updateSchool(Schools mySchool, String schoolCode) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHOOL_CODE, mySchool.getSchool_Code());
        contentValues.put(SCHOOL_NAME, mySchool.getSchool_Name());
        contentValues.put(SCHOOL_BOARD, mySchool.getBoard());
        contentValues.put(SCHOOL_PASSWORD, mySchool.getPassword());
        contentValues.put(SCHOOL_HEADMASTER_NAME, mySchool.getHeadMaster_Name());
        contentValues.put(SCHOOL_HEADMASTER_EMAIL, mySchool.getHeadMaster_Email());
        contentValues.put(SCHOOL_HEADMASTER_PHONE, mySchool.getHeadMaster_PhNo());
        contentValues.put(SCHOOL_COORDINATOR_NAME, mySchool.getCoordinator_Name());
        contentValues.put(SCHOOL_COORDINATOR_PHONE, mySchool.getCoordinator_PhNo());
        contentValues.put(SCHOOL_ADDRESS,mySchool.getAddress());
        contentValues.put(SCHOOL_TOWN, mySchool.getTown());
        contentValues.put(SCHOOL_DISTRICT, mySchool.getDistrict());
        contentValues.put(SCHOOL_STATE, mySchool.getState());
        contentValues.put(SCHOOL_PINCODE, mySchool.getPinCode());
        contentValues.put(SCHOOL_SUBJUNIOIRS, mySchool.getSj());
        contentValues.put(SCHOOL_HOSTEL,mySchool.getHostel());
        contentValues.put(SCHOOL_JUNIORS, mySchool.getJ());
        contentValues.put(SCHOOL_SENIORS, mySchool.getS());
        contentValues.put(SCHOOL_TOTAL, mySchool.getTotal());
        contentValues.put(SCHOOL_PARTICIPANTS, mySchool.getStudentCount());
        contentValues.put(SCHOOL_BOYS, mySchool.getBoysCount());
        contentValues.put(SCHOOL_GIRLS, mySchool.getGirlsCount());
        contentValues.put(SCHOOL_HOSTEL_PARTICIPANTS, mySchool.getStudentHostelCount());
        contentValues.put(SCHOOL_HOSTEL_BOYS, mySchool.getBoysHostelCount());
        contentValues.put(SCHOOL_HOSTEL_GIRLS, mySchool.getGirlsHostelCount());
        contentValues.put(SCHOOL_HOSTEL_DATE1,mySchool.getDate1());
        contentValues.put(SCHOOL_HOSTEL_DATE2,mySchool.getDate2());
        contentValues.put(SCHOOL_HOSTEL_DATE3,mySchool.getDate3());
        contentValues.put(SCHOOL_BOARDING_PLACE,mySchool.getBoardingPlace());

        db.update(SCHOOL_TABLE, contentValues, "schoolCode = ?", new String[]{schoolCode});

    }


    @SuppressLint("Range")
    public Schools getSchool(String schoolCode) {
        SQLiteDatabase db = getReadableDatabase();
        Schools mySchool = new Schools();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SCHOOL_TABLE, null);
        if (cursor.moveToNext()) {
            Log.i("test - Db", new StringBuilder().append(cursor.getString(cursor.getColumnIndex(SCHOOL_CODE))).append(" : ").append(schoolCode).toString());
            if (cursor.getString(cursor.getColumnIndex(SCHOOL_CODE)).equalsIgnoreCase(schoolCode)) {
                mySchool.setSchool_Code(cursor.getString(cursor.getColumnIndex(SCHOOL_CODE)));
                mySchool.setSchool_Name(cursor.getString(cursor.getColumnIndex(SCHOOL_NAME)));
                mySchool.setBoard(cursor.getString(cursor.getColumnIndex(SCHOOL_BOARD)));
                mySchool.setPassword(cursor.getString(cursor.getColumnIndex(SCHOOL_PASSWORD)));
                mySchool.setHeadMaster_Name(cursor.getString(cursor.getColumnIndex(SCHOOL_HEADMASTER_NAME)));
                mySchool.setHeadMaster_Email(cursor.getString(cursor.getColumnIndex(SCHOOL_HEADMASTER_EMAIL)));
                mySchool.setHeadMaster_PhNo(cursor.getString(cursor.getColumnIndex(SCHOOL_HEADMASTER_PHONE)));
                mySchool.setCoordinator_Name(cursor.getString(cursor.getColumnIndex(SCHOOL_COORDINATOR_NAME)));
                mySchool.setCoordinator_PhNo(cursor.getString(cursor.getColumnIndex(SCHOOL_COORDINATOR_PHONE)));
                mySchool.setTown(cursor.getString(cursor.getColumnIndex(SCHOOL_TOWN)));
                mySchool.setDistrict(cursor.getString(cursor.getColumnIndex(SCHOOL_DISTRICT)));
                mySchool.setState(cursor.getString(cursor.getColumnIndex(SCHOOL_STATE)));
                mySchool.setAddress(cursor.getString(cursor.getColumnIndex(SCHOOL_ADDRESS)));
                mySchool.setPinCode(cursor.getString(cursor.getColumnIndex(SCHOOL_PINCODE)));
                Log.i("Test-Login_Helper",""+cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL)));
                if(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL)).equals("1"))
                    mySchool.setHostel(true);
                else
                    mySchool.setHostel(false);
                mySchool.setSj(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCHOOL_SUBJUNIOIRS))));
                mySchool.setJ(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCHOOL_JUNIORS))));
                mySchool.setS(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCHOOL_SENIORS))));
                mySchool.setTotal(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SCHOOL_TOTAL))));
                mySchool.setBoysCount(cursor.getString(cursor.getColumnIndex(SCHOOL_BOYS)));
                mySchool.setGirlsCount(cursor.getString(cursor.getColumnIndex(SCHOOL_GIRLS)));
                mySchool.setStudentCount(cursor.getString(cursor.getColumnIndex(SCHOOL_PARTICIPANTS)));
                mySchool.setBoysHostelCount(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_BOYS)));
                mySchool.setGirlsHostelCount(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_GIRLS)));
                mySchool.setStudentHostelCount(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_PARTICIPANTS)));
                mySchool.setDate1(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_DATE1)));
                mySchool.setDate2(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_DATE2)));
                mySchool.setDate3(cursor.getString(cursor.getColumnIndex(SCHOOL_HOSTEL_DATE3)));
                mySchool.setBoardingPlace(cursor.getString(cursor.getColumnIndex(SCHOOL_BOARDING_PLACE)));
            }
        }
        return mySchool;
    }

    public Integer deleteSchool(String schoolCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SCHOOL_TABLE, "schoolCode = ?", new String[]{schoolCode});
    }
}
