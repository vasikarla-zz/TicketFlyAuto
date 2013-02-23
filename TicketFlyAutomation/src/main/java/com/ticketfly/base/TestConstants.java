package com.ticketfly.base;

public class TestConstants {

	public TestConstants() {
	}

	// Wait time in seconds

	
    public static final long WAIT_SLEEP = 5;
	public static final long MINI_WAIT = 5;
	public static final long SHORT_WAIT = 5;
	public static final long MEDIUM_WAIT = 5;
	public static final long LONG_WAIT = 5;
	public static final String SWITCH_TO_TYPE_WINDOW = "WINDOW";
	public static final String SWITCH_TO_TYPE_FRAME = "FRAME";
	public static final String ALERT_ACTION_TYPE_ACCEPT = "ACCEPT";
	public static final String ALERT_ACTION_TYPE_DISMISS = "DISMISS";
	public static final String SELECT_BY_VALUE = "value";
	public static final String SELECT_BY_INDEX = "index";
	public static final String SELECT_BY_VISIBLE_TEXT = "visibleText";
	public static final String DESELECT_ALL = "deselectAll";
	public static final String SELECT_INFO_TYPE_OPTIONS = "OPTIONS";
	public static final String SELECT_INFO_TYPE_ALL_SELECTED_OPTIONS = "ALL_SELECTED_OPTIONS";
	public static final String SELECT_INFO_TYPE_FIRST_SELECTED_OPTION = "FIRST_SELECTED_OPTION";
	public static final String BASE_PATH = "src/main/resources/data/objectdata/";
	public static  final String BASE_PATH_TEST_DATA = "src/test/resources/data/testdata/";
	
	public static enum fieldTypes {
		text, select,dropdown, textarea, lookup, checkbox, label, radio, button, multiselectpicklist,date
	};
	
		
	public static enum fieldTable {
		Locator, Field_Label, Data_type, Default_Values, Required_Field, Permissions
	}

	public static enum formUtilActions {
		  fillForm, validateNewForm, validateLovs
	};
	
}
