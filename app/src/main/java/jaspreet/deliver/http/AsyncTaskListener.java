/**
@auther Jaspreet Singh Chhabra

Senior software Developer 

Wegile, Mohali Branch
**/
package jaspreet.deliver.http;


public interface AsyncTaskListener {
	public void onHttpResponse(String callResponse, int pageId);
	public void onError(String error, int pageId);
}
