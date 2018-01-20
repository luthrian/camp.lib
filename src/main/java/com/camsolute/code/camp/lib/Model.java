/*******************************************************************************
 * Copyright (C) 2017 Christopher Campbell (campbellccc@gmail.com)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * 	Christopher Campbell (campbellccc@gmail.com) - all code prior and post initial release
 ******************************************************************************/
package com.camsolute.code.camp.lib;

import java.sql.Timestamp;
import java.time.Instant;

import com.camsolute.code.camp.core.U;
import com.camsolute.code.camp.core.interfaces.HasHistory;
import com.camsolute.code.camp.core.interfaces.HasId;
import com.camsolute.code.camp.core.interfaces.HasProduct;
import com.camsolute.code.camp.core.interfaces.HasStatus;
import com.camsolute.code.camp.core.interfaces.HasStatusChange;
import com.camsolute.code.camp.core.interfaces.HasVersion;
import com.camsolute.code.camp.core.interfaces.IsDisplayable;
import com.camsolute.code.camp.core.interfaces.IsStorable;
import com.camsolute.code.camp.core.types.CampHistory;
import com.camsolute.code.camp.core.types.IOStates;
import com.camsolute.code.camp.models.business.Product;

public class Model implements HasId,HasVersion,HasProduct,HasStatus {
//public class Model implements HasId,HasVersion,HasProduct,HasStatus,HasStatusChange,IsDisplayable,IsStorable{
	private final static int NEW_ID = 0;
	private int id = NEW_ID;
	private final String name;
	private Timestamp releaseDate = Timestamp.from(Instant.now());
	private Timestamp endOfLife = U.timestamp("2020-01-01 00:00:00"); 
	private final Version version;
	public static enum ModelStatus { 
		CREATED, INDESIGN, INDEVELOPMENT, SUBMITTED, INREVIEW, RELEASED, RECALLED, DECOMISSIONED, MODIFIED, DIRTY,CLEAN;
	}
	private ModelStatus status = ModelStatus.CREATED;
	private ModelStatus previousStatus = ModelStatus.CLEAN;
	
//	private boolean dirty = false;
//	private boolean modified = false;
//	private boolean loaded = false;
//	private boolean saved = false;
//	private boolean novelty = true;

	private IOStates states;
	
	private Product<?> product;
	
	public Model(int id, String version){
		this.id = id;
		this.name = null;
		this.version = new Version(version);
	}
	
	public Model(int id,Version version){
		this.id = id;
		this.name = null;
		this.version = version;
	}
	
	public Model(String name, String version){
		this.name = name;
		this.version = new Version(version);
	}
	
	public Model(String name,Version version){
		this.name = name;
		this.version = version;
	}
	
	@Override
	public int id(){
		return id;
	}
	@Override
	public int updateId(int id){
		return this.id = id;
	}
	public String name() {
		return name;
	}
	public Timestamp releaseDate(){
		return this.releaseDate;
	}
	public Timestamp releaseDate(Timestamp date){
		return this.releaseDate = date;
	}

	public Timestamp endOfLife(){
		return this.endOfLife;
	}
	public Timestamp endOfLife(Timestamp eol){
		return this.endOfLife = eol;
	}

	@Override
	public Version version() {
		return version;
	}

	@Override
	public Product<?> product() {
		return this.product;
	}

	@Override
	public Product<?> product(Product<?> product) {
		return this.product = product;
	}

//	@Override
	public ModelStatus status() {
		return this.status;
	}

//	@Override
	public ModelStatus updateStatus(Enum<?> status) {
		ModelStatus prev = this.status;
		this.status = (ModelStatus)status;
		this.states().modify();
		return prev;
	}

//	@Override
	public ModelStatus setStatus(Enum<?> status) {
		ModelStatus prev = this.status;
		this.status = (ModelStatus)status;
		return prev;
	}

//	@Override
	public ModelStatus previousStatus() {
		return this.previousStatus;
	}

//	@Override
	public ModelStatus previousStatus(Enum<?> status) {
		return this.previousStatus =  (ModelStatus) status;
	}

//	@Override
	public ModelStatus cleanStatus() {
		if(states().isModified() && this.status.equals(ModelStatus.MODIFIED)) {
			this.status = this.previousStatus;
			this.previousStatus = ModelStatus.CLEAN;
			this.states().modify();
		}
		
		return status();
	}

//	@Override
//	public boolean isModified() {
//		return this.modified;
//	}
//
//	@Override
//	public void modify() {
//		if(!this.modified){
//			this.modified = true;
//			this.previousStatus = this.status;
//			this.status = ModelStatus.MODIFIED;
//		}
//	}
//
//	@Override
//	public void modify(boolean t) {
//		this.modified = t;
//	}
//
//	@Override
//	public boolean isDirty() {
//		return this.dirty;
//	}
//
//	@Override
//	public void dirty() {
//		this.dirty = true;
//	}

//	@Override
//	public boolean wasLoaded() {
//		return this.loaded;
//	}
//
//	@Override
//	public void loaded() {
//		this.saved = false;
//		this.loaded = true;
//		this.novelty = false;
//	}
//
//	@Override
//	public void loaded(boolean loaded) {
//		this.loaded = loaded;
//	}
//
//	@Override
//	public boolean wasSaved() {
//		return this.saved;
//	}
//
//	@Override
//	public void saved() {
//		this.loaded = false;
//		this.saved = true;
//		this.novelty = false;
//	}
//	
//	@Override
//	public void saved(boolean saved) {
//		this.saved = saved;
//	}
//	
//	@Override
//	public boolean isNew() {
//		return this.novelty;
//	}
//	
//	@Override
//	public void isNew(boolean novelty) {
//		this.novelty = novelty;
//	}

	public String print() {
		String r = "\nid: "+id+"\n"
			+ "name: "+name+"\n"
			+ "version: "+version.value()+"\n"
			+ "release date: "+releaseDate+"\n"
			+ "end of life: "+endOfLife+"\n"
			+ "status: "+states.status().name()+"\n"
			+ "previousStatus: "+states.previousStatus().name()+"\n"
			+ "dirty: "+states.isDirty()+"\n"
			+ "modified: "+states.isModified()+"\n"
			+ "loaded: "+states.wasLoaded()+"\n"
			+ "saved: "+states.wasSaved()+"\n"
			+ "is new: "+states.isNew()+"\n";
		return r;
	}

	@Override
	public IOStates states() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public boolean readyToSave() {
//		return true;
//	}

}
