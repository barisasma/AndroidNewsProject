package com.android_gazete;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import com.android_gazete.R;

import android.content.Context;
import android.test.PerformanceTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

public class GazeteArrayAdapter extends ArrayAdapter<Gazete>{
	
	//public GazeteArrayAdapter(Context context, ArrayList<Gazete> list) {
			// TODO Auto-generated constructor stub
			//super(context,R.layout.list_row_small,list);
			
		//}
	 Context context; 
	 int layoutResourceId;   
	 ArrayList<Gazete> list;
	 ArrayList<Gazete> originalList;
	 private JournalFilter filter;
	
	public GazeteArrayAdapter(Context context, ArrayList<Gazete> list, int layoutresourceid)
	{
		super(context,layoutresourceid,list);
		this.layoutResourceId=layoutresourceid;
		this.context=context;
		this.list=new ArrayList<Gazete>();
		this.list.addAll(list);
		this.originalList=new ArrayList<Gazete>();
		this.originalList.addAll(list);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//final Gazete gazete= getItem(position);
		 View vi=convertView;
		 ImageHolder holder = null;
		 
	    if(convertView==null){
	            vi = LayoutInflater.from(getContext()).inflate(layoutResourceId, null);
	            
	            holder = new ImageHolder();		
	            
	            holder.txtName=(TextView)vi.findViewById(R.id.title); // name
	            holder.txtUrl=(TextView)vi.findViewById(R.id.adress);// url
	            holder.imgIcon=(ImageView)vi.findViewById(R.id.list_image);
	            vi.setTag(holder);}
	            else{
	            	holder=(ImageHolder) vi.getTag();
	            }
	    		Gazete gazete = list.get(position);
	    		holder.txtName.setText(gazete.getName());
	    		holder.txtUrl.setText(gazete.getUrl());
	    		//convert byte to bitmap take from gazete class
	    		byte[] outimage=gazete.getImage();
	    		ByteArrayInputStream imageStream = new ByteArrayInputStream(outimage);
	    		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
	    		holder.imgIcon.setImageBitmap(theImage);
   
        return vi;
	}
	
	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		if(filter==null)
		 filter = new JournalFilter();
		return filter;
	}
	
	static class ImageHolder
	 {
	        ImageView imgIcon;
	        TextView txtName;
	        TextView txtUrl;
	 }
	
	private class JournalFilter extends Filter{

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			 constraint = constraint.toString().toLowerCase();
			    FilterResults result = new FilterResults();
			    if(constraint != null && constraint.toString().length() > 0)
			    {
			    ArrayList<Gazete> filteredItems = new ArrayList<Gazete>();
			 
			    for(int i = 0, l = originalList.size(); i < l; i++)
			    {
				     Gazete gazete = originalList.get(i);
				     if(gazete.getName().toString().toLowerCase().contains(constraint))
				      filteredItems.add(gazete);
			    }
					    result.count = filteredItems.size();
					    result.values = filteredItems;
			    }
			    else
			    {
			     synchronized(this)
			     {
			      result.values = originalList;
			      result.count = originalList.size();
			     }
			    }
			    return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			list = (ArrayList<Gazete>)results.values;
		    notifyDataSetChanged();
		    clear();
		    for(int i = 0, l = list.size(); i < l; i++)
		     add(list.get(i));
		    notifyDataSetInvalidated();
			
		} 
	}

}
