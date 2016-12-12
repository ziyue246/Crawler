package refer.util;

import java.sql.Timestamp;
import java.util.*;

public class ListUtil {
	/**
	 * 在list 中匹配id，返回索引位置，不存在则返回-1
	 * @param id
	 * @param list
	 * @return
	 */
	public static int indexOfList(Integer id,List<Object[]>list){
		for(int i=0;i<list.size();i++){
			if(list.get(i)[0]==null && id==null)
				return i;
			if(list.get(i)[0]!=null && list.get(i)[0].equals(id))
				return i;
		}
		return -1;
	}

	public static int indexOfList2(Date d,List<Date>list){
		for(int i=0;i<list.size();i++){
			if(list.get(i)==null&&d==null)
				return i;
			if(list.get(i)!=null&&list.get(i).equals(d))
				return i;
		}
		return -1;
	}


	public static int indexOfList2(Timestamp d,List<Date>list){
		for(int i=0;i<list.size();i++){
			if(list.get(i)==null&&d==null)
				return i;
			if(list.get(i)!=null&&list.get(i).getTime()==d.getTime())
				return i;
		}
		return -1;
	}
	public static int indexOfList2(Object d,List<Date>list){
		if(d instanceof Timestamp){
			return indexOfList2((Timestamp)d,list);
		}else if(d instanceof Date){
			return indexOfList2((Date)d,list);
		}else{
			return -1;
		}
	}

	public static void full0(List<Long>list,int size){
		list.clear();
		for(int i=0;i<size;i++)
			list.add(0l);
	}
	public static <K,T> Map<K, List<T>> groupBy(String group,Class<K> c,
			List<T> list) {
		Map<K, List<T>>map=new HashMap<K, List<T>>();
		for (T p : list) {
			K g=(K)ReflectUtil.getter(p, group);
			if(map.containsKey(g)){
				List<T>maplist=map.get(g);
				maplist.add(p);
			}else{
				List<T>maplist=new ArrayList<T>();
				maplist.add(p);
				map.put(g, maplist);
			}
		}
		return map;
	}
}
