package jcal;

import java.awt.*;
import javax.swing.* ;
import java.awt.event.* ;
import javax.swing.event.* ;
import javax.swing.text.*;
import java.text.DateFormatSymbols;

import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This is Date text field that can be used to show java.util.Calendar or java.util.Date in a text field.
 * The date can be shown in different formats.
 * @author : Niraj Agarwal
 * @version : 1.0
 * @jdk version tested on : 1.4
 */
public class JDateTextField extends JTextField
{
    public static final int US_SHORT_DATE = 11;
    public static final int IN_SHORT_DATE = 12;
    public static final int LONG_DATE = 13;

    private static final char FW_SLASH = '/';
    private static final char DASH = '-';

    private static final String FM_US_SHORT_DATE = "MM/dd/yyyy";
    private static final String FM_IN_SHORT_DATE = "dd/MM/yyyy";
    private static final String FM_LONG_DATE = "dd-MMM-yyyy";

    private static final DateFormatSymbols   dfSymb  = new DateFormatSymbols();
    private static final String  SHORT_MONTHS[] = dfSymb.getShortMonths();

    private static final String NULL_SHORT_DATE = "//";
    private static final String NULL_LONG_DATE = "--";

    private int format = US_SHORT_DATE;
    private String mask = FM_US_SHORT_DATE;

    Calendar date = null;

    public JDateTextField()
    {
        this(US_SHORT_DATE);
    }

    public JDateTextField(int iFormat)
    {
        setFormat(iFormat);
        super.setEditable(false);
    }

    public void setText(String text)
    {
        try
        {
            setDate(text);
        }
        catch(ParseException pEx)
        {
            validateNull(null);
        }
    }

    public String getText()
    {
        return getText(format);
    }

    public String getText(int iFormat)
    {
        return formateDate(date,iFormat);
    }

    public void setDate(String text)
    throws ParseException
    {
        if( text == null )
            validateNull(null);
        else
            validateDate(text);
    }

    public void setEnabled(boolean enable)
    {
        super.setEnabled(enable);

        if( enable )
        {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        else
        {
            setBackground(Color.lightGray);
            setForeground(Color.darkGray);
        }
    }

    public void setEditable(boolean enable)
    {
        if( enable )
        {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        else
        {
            setBackground(Color.lightGray);
            setForeground(Color.darkGray);
        }
    }

    public void setCalendar(Calendar cDate)
    {
        this.date = cDate;

        if(cDate == null)
             validateNull(null);
        else
            super.setText(formateDate(cDate,format));
    }

    public Calendar getCalendar()
    {
        return this.date;
    }

    public void setDate(Date uDate)
    {
        if(uDate == null)
        {
            validateNull(null);
            return;
        }

        Calendar cDate = Calendar.getInstance();
        cDate.clear();
        cDate.setTime(uDate);

        setCalendar(cDate);
    }

    public Date getDate()
    {
        if( this.date == null )
            return null;
        else
            return this.date.getTime();
    }

    public void setFormat(int iFormat)
    {
        switch(iFormat)
        {
            case US_SHORT_DATE:
            default:
                super.setText(NULL_SHORT_DATE);
                format = US_SHORT_DATE;
                mask = FM_US_SHORT_DATE;
                break;

            case IN_SHORT_DATE:
                super.setText(NULL_SHORT_DATE);
                format = IN_SHORT_DATE;
                mask = FM_IN_SHORT_DATE;
                break;

            case LONG_DATE:
                super.setText(NULL_LONG_DATE);
                format = LONG_DATE;
                mask = FM_LONG_DATE;
                break;
        }
    }

    private void validateNull(String text)
    {
        if( format == US_SHORT_DATE && text == null )
            super.setText(NULL_SHORT_DATE);
        else if( format == IN_SHORT_DATE && text == null )
            super.setText(NULL_SHORT_DATE);
        else if( format == LONG_DATE && text == null )
            super.setText(NULL_LONG_DATE);

        this.date = null;
    }

    private void validateDate(String text)
    throws ParseException
    {
        int textLength = text.length();

        if( format == US_SHORT_DATE )
        {
            if( textLength > 10 )
                throw new ParseException("Length of passed date ("+text+") must be 10 atmost : "+FM_US_SHORT_DATE, textLength);

            validateUSShortDate(text);
        }
        else if( format == IN_SHORT_DATE )
        {
            if( textLength > 10 )
                throw new ParseException("Length of passed date ("+text+") must be 10 atmost : "+FM_IN_SHORT_DATE, textLength);

            validateINShortDate(text);
        }
        else if( format == LONG_DATE )
        {
            if( textLength > 11 )
                throw new ParseException("Length of passed date ("+text+") must be 11 atmost : "+FM_LONG_DATE, textLength);

            validateLongDate(text);
        }
    }

    private void validateUSShortDate(String text)
    throws ParseException
    {
        int iFirst = -1, iLast = -1, tokens = 0;
        for(int i=0; i<text.length(); i++)
        {
            char c = text.charAt(i);
            if( c == FW_SLASH )
            {
                tokens++;
                if( iFirst == -1 )
                    iFirst = i;
                else
                    iLast = i;
            }
        }

        if( tokens != 2 )
            throw new ParseException( "Insufficient fields ("+text+"), need 3 fields in format: "+FM_US_SHORT_DATE, 0);

        String  sMonth  = text.substring(0,iFirst);
        String  sDate   = text.substring(iFirst+1,iLast);
        String  sYear   = text.substring(iLast+1);

        int iMonth = -1, iDate = -1, iYear = -1;

        try
        {
            iMonth  = Integer.parseInt(sMonth);
            iDate   = Integer.parseInt(sDate);
            iYear   = Integer.parseInt(sYear);
        }
        catch(NullPointerException npEx)
        {
            throw new ParseException(npEx.getMessage(),0);
        }
        catch(NumberFormatException nfEx)
        {
            throw new ParseException(nfEx.getMessage(),0);
        }

        iMonth--;
        if( iMonth < 0 || iMonth > 11 )
            throw   new ParseException( "Month field should be between 1 and 12.", iMonth);

        int days = getDaysInMonth(iYear,iMonth);
        if( iDate < 1 || iDate > days )
            throw new ParseException( "Date field should be between 1 and "+days+".", iDate);

        if( iYear < 0 )
            throw new ParseException( "Year field can't be negative.", iYear);

        // If only two digits of year are specified ...
        if( iYear < 100 )
        {
            // Use the current year ...
            iYear += Calendar.getInstance().get(Calendar.YEAR) / 100 * 100;
        }

        // Create the Calendar object with the converted values ...
        Calendar cDate = Calendar.getInstance();
        cDate.clear();
        cDate.set( iYear, iMonth, iDate );

        this.date = cDate;
        super.setText(formateDate(cDate, format));
    }

    private void validateINShortDate(String text)
    throws ParseException
    {
        int iFirst = -1, iLast = -1, tokens = 0;
        for(int i=0; i<text.length(); i++)
        {
            char c = text.charAt(i);
            if( c == FW_SLASH )
            {
                tokens++;
                if( iFirst == -1 )
                    iFirst = i;
                else
                    iLast = i;
            }
        }

        if( tokens != 2 )
            throw new ParseException( "Insufficient fields ("+text+"), need 3 fields in format: "+FM_IN_SHORT_DATE, 0);

        String  sDate   = text.substring(0,iFirst);
        String  sMonth  = text.substring(iFirst+1,iLast);
        String  sYear   = text.substring(iLast+1);

        int iDate = -1, iMonth = -1, iYear = -1;

        try
        {
            iDate   = Integer.parseInt(sDate);
            iMonth  = Integer.parseInt(sMonth);
            iYear   = Integer.parseInt(sYear);
        }
        catch(NullPointerException npEx)
        {
            throw new ParseException(npEx.getMessage(),0);
        }
        catch(NumberFormatException nfEx)
        {
            throw new ParseException(nfEx.getMessage(),0);
        }

        int days = getDaysInMonth(iYear,iMonth);
        if( iDate < 1 || iDate > days )
            throw new ParseException( "Date field should be between 1 and "+days+".", iDate);

        iMonth--;
        if( iMonth < 0 || iMonth > 11 )
            throw   new ParseException( "Month field should be between 1 and 12.", iMonth);

        if( iYear < 0 )
            throw new ParseException( "Year field can't be negative.", iYear);

        // If only two digits of year are specified ...
        if( iYear < 100 )
        {
            // Use the current year ...
            iYear += Calendar.getInstance().get(Calendar.YEAR) / 100 * 100;
        }

        // Create the Calendar object with the converted values ...
        Calendar cDate = Calendar.getInstance();
        cDate.clear();
        cDate.set( iYear, iMonth, iDate );

        this.date = cDate;
        super.setText(formateDate(cDate, format));
    }

    private void validateLongDate(String text)
    throws ParseException
    {
        int iFirst = -1, iLast = -1, tokens = 0;
        for(int i=0; i<text.length(); i++)
        {
            char c = text.charAt(i);
            if( c == DASH )
            {
                tokens++;
                if( iFirst == -1 )
                    iFirst = i;
                else
                    iLast = i;
            }
        }

        if( tokens != 2 )
            throw new ParseException( "Insufficient fields ("+text+"), need 3 fields in format: "+FM_LONG_DATE, 0);

        String  sDate   = text.substring(0,iFirst);
        String  sMonth  = text.substring(iFirst+1,iLast).toUpperCase();
        String  sYear   = text.substring(iLast+1);

        int iDate = -1, iMonth = -1, iYear = -1;

        try
        {
            iDate   = Integer.parseInt(sDate);
            iYear   = Integer.parseInt(sYear);
        }
        catch(NullPointerException npEx)
        {
            throw new ParseException(npEx.getMessage(),0);
        }
        catch(NumberFormatException nfEx)
        {
            throw new ParseException(nfEx.getMessage(),0);
        }

        int days = getDaysInMonth(iYear,iMonth);
        if( iDate < 1 || iDate > days )
            throw new ParseException( "Date field should be between 1 and "+days+".", iDate);

        for( iMonth=0; iMonth<SHORT_MONTHS.length && !SHORT_MONTHS[iMonth].toUpperCase().equals(sMonth); iMonth++);
        if( iMonth >= SHORT_MONTHS.length )
            throw   new ParseException( "Month field doesn't start with 'Jan' - 'Dec' :"+sMonth, 0);

        if( iYear < 0 )
            throw new ParseException( "Year field can't be negative.", iYear);

        // If only two digits of year are specified ...
        if( iYear < 100 )
        {
            // Use the current year ...
            iYear += Calendar.getInstance().get(Calendar.YEAR) / 100 * 100;
        }

        // Create the Calendar object with the converted values ...
        Calendar cDate = Calendar.getInstance();
        cDate.clear();
        cDate.set( iYear, iMonth, iDate );

        this.date = cDate;
        super.setText(formateDate(cDate, format));
    }

    private static int getDaysInMonth(int iYear, int iMonth)
    {
        switch( iMonth )
        {
            case 3:
            case 5:
            case 8:
            case 10:
            return  30;

            case 1:     // February
                return ((iYear%4)==0 || (iYear%200)==0) ? 29 : 28;

            default:
                return  31;
        }
    }

    public String formateDate(Calendar cDate, int iFormat)
    {
        String sReturn = null;
        if( date == null )
            return sReturn;

        SimpleDateFormat xlsDateFormater;

        if( iFormat == US_SHORT_DATE )
        {
            xlsDateFormater = new SimpleDateFormat(FM_US_SHORT_DATE);
            sReturn = xlsDateFormater.format(cDate.getTime());
        }
        else if( iFormat == IN_SHORT_DATE )
        {
            xlsDateFormater = new SimpleDateFormat(FM_IN_SHORT_DATE);
            sReturn = xlsDateFormater.format(cDate.getTime());
        }
        else if( iFormat == LONG_DATE )
        {
            xlsDateFormater = new SimpleDateFormat(FM_LONG_DATE);
            sReturn = xlsDateFormater.format(cDate.getTime());
        }

        return sReturn;
    }
}