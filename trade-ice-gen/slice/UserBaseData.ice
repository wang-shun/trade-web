[[ "java:package:com.centaline.ice.gen" ]]
module baseData
{
	dictionary<string, string> ServiceMap;
	
	interface UserBaseData{
		string getDictValue(string dictType, string dictCode);
	};
};