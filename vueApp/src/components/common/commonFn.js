export default{
    test(){
        return 'test';
    },
    dataParse(res){
        let data = res.data;
        if(data.resCode == 200){
            return data.data;
        }else{
            alert('获取数据出错,请查看控制台');
            console.log(res);
        }

    }
}