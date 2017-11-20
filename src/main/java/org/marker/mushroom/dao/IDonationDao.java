package org.marker.mushroom.dao;

import org.marker.mushroom.beans.Donation;


public interface IDonationDao extends ISupportDao
{

	public boolean update(Donation entity);

}
