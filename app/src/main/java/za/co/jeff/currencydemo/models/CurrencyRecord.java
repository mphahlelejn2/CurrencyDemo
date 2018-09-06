
package za.co.jeff.currencydemo.models;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import static za.co.jeff.currencydemo.models.Currency.CURRENCY_CODE;
import static za.co.jeff.currencydemo.models.CurrencyRecord.CURRENCYRECORD_CODE;
import static za.co.jeff.currencydemo.models.CurrencyRecord.CURRENCYRECORD_DI;

@Entity(tableName = CurrencyRecord.CURRENCYRECORD, indices = {
        @Index(value = {CURRENCYRECORD_CODE,CURRENCYRECORD_DI})},
        foreignKeys = {@ForeignKey(entity = Currency.class, parentColumns = CURRENCY_CODE, childColumns = CURRENCYRECORD_CODE,onDelete = ForeignKey.CASCADE)}
        )
public class CurrencyRecord {

    public static final String CURRENCYRECORD = "currencyRecord";
    public static final String CURRENCYRECORD_DI="currencyRecord_id";
    public static final String CURRENCYRECORD_CODE ="currencyRecord_code";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name=CURRENCYRECORD_DI)
    private int id;

    @ColumnInfo(name=CURRENCYRECORD_CODE)
    private String name;
    private double value;

    public double getValue() {
        return value;
    }
    private String timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
