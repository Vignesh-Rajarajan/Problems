package lld.auctionSystem.repository;


import lld.auctionSystem.models.Auction;

import java.util.HashMap;
import java.util.Map;

public class AuctionRepository {
	public Map<String, Auction> auctions;

	// TODO - Implement thread safe lazy initialization singleton pattern.
	private AuctionRepository() {
		this.auctions = new HashMap<>();
	}
	private static AuctionRepository single_instance = null;
	public static AuctionRepository getInstance() {
		if(single_instance==null) {
			single_instance = new AuctionRepository();
		}
		return single_instance;
	}

	public void addDataToRepository(String key, Auction value) {
		this.auctions.put(key, value);
	}

	public Auction getData(String key) {
		if(key==null) {
			return null;
		}
		return this.auctions.get(key);
	}
}
