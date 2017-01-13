package com.molmc.core.database;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmObject;


/**
 * features: 数据库操作类
 * Author：  hhe on 16-9-1 18:17
 * Email：   hui@molmc.com
 */

public class RealmDB {
	private final static String realmName = "cypress.realm";

	private static Realm sRealm;

	public static Realm getInstanse(){
		sRealm = Realm.getDefaultInstance();
		return sRealm;
	}

	public static void init(Context context){
		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context)
				.name(realmName)
				.build();
		Realm.setDefaultConfiguration(realmConfiguration);
	}


	/**
	 * 添加(性能优于下面的saveOrUpdate（）方法)
	 *
	 * @param object
	 * @return 保存或者修改是否成功
	 */
	public static boolean insert(RealmObject object) {
		try {
			sRealm.beginTransaction();
			sRealm.insert(object);
			sRealm.commitTransaction();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			sRealm.cancelTransaction();
			return false;
		}
	}

	/**
	 * 添加(性能优于下面的saveOrUpdateBatch（）方法)
	 *
	 * @param list
	 * @return 批量保存是否成功
	 */
	public static boolean insert(List<? extends RealmObject> list) {
		try {
			sRealm.beginTransaction();
			sRealm.insert(list);
			sRealm.commitTransaction();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			sRealm.cancelTransaction();
			return false;
		}
	}

	/**
	 * 删除数据
	 * @param clazz
	 * @return
	 */
	public static boolean delete(Class<? extends RealmModel> clazz){
		try {
			sRealm.beginTransaction();
			sRealm.delete(clazz);
			sRealm.commitTransaction();
			return true;
		}catch (Exception e){
			e.printStackTrace();
			sRealm.commitTransaction();
			return false;
		}
	}

	/**
	 * 清空数据库
	 * @return
	 */
	public static boolean deleteAll(){
		try {
			sRealm.beginTransaction();
			sRealm.deleteAll();
			sRealm.commitTransaction();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			sRealm.commitTransaction();
			return false;
		}
	}

}
