package ru.agrin.timingApp.ui;

import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;

/** Представление даты в определенном формате.
 * Created by Grin on 22.02.2017.
 */
    public class DateCellRenderer extends DefaultTableCellRenderer {
        public DateCellRenderer() { super(); }

        @Override
        public void setValue(Object value) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            setText((value == null) ? "" : sdf.format(value));
        }
    }

