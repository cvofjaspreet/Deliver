/**
@auther Jaspreet Singh Chhabra

Senior software Developer 

Wegile, Mohali Branch
**/

package jaspreet.deliver.lists;

import java.util.ArrayList;

import jaspreet.deliver.models.CountyCodeModel;


public class CountryCodeList extends ArrayList<CountyCodeModel> {

	/**
	 * Array List to store the Country code
	 */

	private static CountryCodeList countryCodeList = null;

	private CountryCodeList() {

	}

	public static CountryCodeList getInstance() {

		if (countryCodeList == null) {
			countryCodeList = new CountryCodeList();
		}

		return countryCodeList;

	}

}
