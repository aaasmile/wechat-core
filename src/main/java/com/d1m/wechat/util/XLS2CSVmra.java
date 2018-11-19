package com.d1m.wechat.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RKRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * A XLS -> CSV processor, that uses the MissingRecordAware EventModel code to
 * ensure it outputs all columns and rows.
 * 
 * @author Nick Burch
 */
public class XLS2CSVmra implements HSSFListener {
	private int minColumns;
	private POIFSFileSystem fs;
	private PrintStream output;

	private int lastRowNumber;
	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	// Records we pick up as we process
	private SSTRecord sstRecord;
	private Map customFormatRecords = new Hashtable();
	private List xfRecords = new ArrayList();

	/**
	 * Creates a new XLS -> CSV converter
	 * 
	 * @param fs         The POIFSFileSystem to process
	 * @param output     The PrintStream to output the CSV to
	 * @param minColumns The minimum number of columns to output, or -1 for no
	 *                   minimum
	 */
	public XLS2CSVmra(POIFSFileSystem fs, PrintStream output, int minColumns) {
		this.fs = fs;
		this.output = output;
		this.minColumns = minColumns;
	}

	/**
	 * Creates a new XLS -> CSV converter
	 * 
	 * @param filename   The file to process
	 * @param minColumns The minimum number of columns to output, or -1 for no
	 *                   minimum
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public XLS2CSVmra(String filename, int minColumns) throws IOException, FileNotFoundException {
		this(new POIFSFileSystem(new FileInputStream(filename)), System.out, minColumns);
	}

	/**
	 * Initiates the processing of the XLS file to CSV
	 */
	public void process() throws IOException {
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();
		request.addListenerForAllRecords(listener);

		factory.processWorkbookEvents(request, fs);
	}

	/**
	 * Main HSSFListener method, processes events, and outputs the CSV as the file
	 * is processed.
	 */
	public void processRecord(Record record) {
		int thisRow = -1;
		int thisColumn = -1;
		String thisStr = null;

		switch (record.getSid()) {
		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;
		case FormatRecord.sid:
			FormatRecord fr = (FormatRecord) record;
			customFormatRecords.put(new Integer(fr.getIndexCode()), fr);
			break;
		case ExtendedFormatRecord.sid:
			ExtendedFormatRecord xr = (ExtendedFormatRecord) record;
			xfRecords.add(xr);
			break;

		case BlankRecord.sid:
			BlankRecord brec = (BlankRecord) record;

			thisRow = brec.getRow();
			thisColumn = brec.getColumn();
			thisStr = "";
			break;
		case BoolErrRecord.sid:
			BoolErrRecord berec = (BoolErrRecord) record;

			thisRow = berec.getRow();
			thisColumn = berec.getColumn();
			thisStr = "";
			break;
		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;

			thisRow = frec.getRow();
			thisColumn = frec.getColumn();

			if (outputFormulaValues) {
				thisStr = formatNumberDateCell(frec, frec.getValue());
			} else {
				// TODO: Output the formula string
				thisStr = '"' + frec.toString() + '"';
			}
			break;
		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			thisRow = lrec.getRow();
			thisColumn = lrec.getColumn();
			thisStr = '"' + lrec.getValue() + '"';
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			thisRow = lsrec.getRow();
			thisColumn = lsrec.getColumn();
			if (sstRecord == null) {
				thisStr = '"' + "(No SST Record, can't identify string)" + '"';
			} else {
				thisStr = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
			}
			break;
		case NoteRecord.sid:
			NoteRecord nrec = (NoteRecord) record;

			thisRow = nrec.getRow();
			thisColumn = nrec.getColumn();
			// TODO: Find object to match nrec.getShapeId()
			thisStr = '"' + "(TODO)" + '"';
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			thisRow = numrec.getRow();
			thisColumn = numrec.getColumn();

			// Format
			thisStr = formatNumberDateCell(numrec, numrec.getValue());
			break;
		case RKRecord.sid:
			RKRecord rkrec = (RKRecord) record;

			thisRow = rkrec.getRow();
			thisColumn = rkrec.getColumn();
			thisStr = '"' + "(TODO)" + '"';
			break;
		default:
			break;
		}

		// Handle new row
		if (thisRow != -1 && thisRow != lastRowNumber) {
			lastColumnNumber = -1;
		}

		// Handle missing column
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
			thisRow = mc.getRow();
			thisColumn = mc.getColumn();
			thisStr = "";
		}

		// If we got something to print out, do so
		if (thisStr != null) {
			if (thisColumn > 0) {
				output.print(',');
			}
			output.print(thisStr);
		}

		// Update column and row count
		if (thisRow > -1)
			lastRowNumber = thisRow;
		if (thisColumn > -1)
			lastColumnNumber = thisColumn;

		// Handle end of row
		if (record instanceof LastCellOfRowDummyRecord) {
			// Print out any missing commas if needed
			if (minColumns > 0) {
				// Columns are 0 based
				if (lastColumnNumber == -1) {
					lastColumnNumber = 0;
				}
				for (int i = lastColumnNumber; i < (minColumns); i++) {
					output.print(',');
				}
			}

			// We're onto a new row
			lastColumnNumber = -1;

			// End the row
			output.println();
		}
	}

	/**
	 * Formats a number or date cell, be that a real number, or the answer to a
	 * formula
	 */
	private String formatNumberDateCell(CellValueRecordInterface cell, double value) {
		// Get the built in format, if there is one
		ExtendedFormatRecord xfr = (ExtendedFormatRecord) xfRecords.get(cell.getXFIndex());
		if (xfr == null) {
			System.err.println("Cell " + cell.getRow() + "," + cell.getColumn() + " uses XF with index "
					+ cell.getXFIndex() + ", but we don't have that");
			return Double.toString(value);
		} else {
			int formatIndex = xfr.getFormatIndex();
			String format;
			if (formatIndex >= HSSFDataFormat.getNumberOfBuiltinBuiltinFormats()) {
				FormatRecord tfr = (FormatRecord) customFormatRecords.get(new Integer(formatIndex));
				format = tfr.getFormatString();
			} else {
				format = HSSFDataFormat.getBuiltinFormat(xfr.getFormatIndex());
			}

			// Is it a date?
			if (HSSFDateUtil.isADateFormat(formatIndex, format) && HSSFDateUtil.isValidExcelDate(value)) {
				// Java wants M not m for month
				format = format.replace('m', 'M');
				// Change \- into -, if it's there
				format = format.replaceAll("\\\\-", "-");

				// Format as a date
				Date d = HSSFDateUtil.getJavaDate(value);
				DateFormat df = new SimpleDateFormat(format);
				return df.format(d);
			} else {
				if (format == "General") {
					// Some sort of wierd default
					return Double.toString(value);
				}

				// Format as a number
				DecimalFormat df = new DecimalFormat(format);
				return df.format(value);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 1) {
			System.err.println("Use:");
			System.err.println("  XLS2CSVmra <xls file> [min columns]");
			System.exit(1);
		}

		int minColumns = -1;
		if (args.length >= 2) {
			minColumns = Integer.parseInt(args[1]);
		}

		XLS2CSVmra xls2csv = new XLS2CSVmra(args[0], minColumns);
		xls2csv.process();
	}
}
