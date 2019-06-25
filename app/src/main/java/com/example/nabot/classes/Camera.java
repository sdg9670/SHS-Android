// Copyright © 2016-2018 Shawn Baker using the MIT License.
package com.example.nabot.classes;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Camera implements Comparable<Camera>, Parcelable
{
	// local constants
	//private final static String TAG = "Camera";

	// instance variables
	public String network;
	public String name;
	public String address;
	public int port;

	//******************************************************************************
	// Camera
	//******************************************************************************
	public Camera(String name, String address, int port)
	{
		this.network = "mola";
		this.name = name;
		this.address = address;
		this.port = port;
	}

	//******************************************************************************
	// Camera
	//******************************************************************************
	public Camera(Camera camera)
	{
		network = camera.network;
		name = camera.name;
		address = camera.name;
		port = camera.port;
	}

	//******************************************************************************
	// Camera
	//******************************************************************************
	public Camera(Parcel in)
	{
		readFromParcel(in);
	}

	//******************************************************************************
	// initialize
	//******************************************************************************
	private void initialize()
	{
		network = Utils.getNetworkName();
		name = Utils.getDefaultCameraName();
		address = Utils.getBaseIpAddress();
		port = Utils.getDefaultPort();
	}

	//******************************************************************************
	// writeToParcel
	//******************************************************************************
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(network);
		dest.writeString(name);
		dest.writeString(address);
		dest.writeInt(port);
	}

	//******************************************************************************
	// readFromParcel
	//******************************************************************************
	private void readFromParcel(Parcel in)
	{
		network = in.readString();
		name = in.readString();
		address = in.readString();
		port = in.readInt();
	}

	//******************************************************************************
	// describeContents
	//******************************************************************************
	public int describeContents()
	{
		return 0;
	}

	//******************************************************************************
	// Parcelable.Creator
	//******************************************************************************
	public static final Creator CREATOR = new Creator()
	{
		public Camera createFromParcel(Parcel in)
		{
			return new Camera(in);
		}
		public Camera[] newArray(int size)
		{
			return new Camera[size];
		}
	};

	//******************************************************************************
	// equals
	//******************************************************************************
    @Override
    public boolean equals(Object otherCamera)
    {
		if (otherCamera instanceof Camera)
		{
			return compareTo((Camera)otherCamera) == 0;
		}
		return false;
    }

	//******************************************************************************
	// compareTo
	//******************************************************************************
    @Override
    public int compareTo(Camera camera)
    {
		int result = 1;
		if (camera != null)
		{
			result = name.compareTo(camera.name);
			if (result == 0)
			{
				result = address.compareTo(camera.address);
				if (result == 0)
				{
					result = port - camera.port;
					if (result == 0)
					{
						result = network.compareTo(camera.network);
					}
				}
			}
		}
        return result;
    }

	//******************************************************************************
	// toString
	//******************************************************************************
    @Override
    public String toString()
    {
        return name + "," + network + "," + address + "," + port;
    }

	//******************************************************************************
	// toJson
	//******************************************************************************
	JSONObject toJson()
	{
		try
		{
			JSONObject obj = new JSONObject();
			obj.put("network", network);
			obj.put("name", name);
			obj.put("address", address);
			obj.put("port", port);
			return obj;
		}
		catch(JSONException ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
}
