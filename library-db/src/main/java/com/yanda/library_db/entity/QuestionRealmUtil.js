
import realm from './realm';




/***考点id查询章节练习数据并组装**/
export function queryFromRealmWithPointId(pointId) {

        let queryStr='pointId = "'+pointId+'" AND randomSelect = 0 AND status = 0';
        let questionObj = realm.objects('Question').filtered(queryStr).sorted('id');
        let objStr = JSON.stringify(questionObj);

        let result=JSON.parse(objStr);
        let resultArray=[];
        if(result!=undefined){
            resultArray=Array.from(result);
            resultArray.forEach((question,i) => {
                question.idStr=question.id;//str
                question.doNum=0;//全站做题数
                question.errorNum=0;//全站做错题数
                question.firstNum=0;//第一次做题人数
                question.firstErrorNum=0;//第一次做错题人数
                question.commentNum=0;//回复数
                question.userAnswer='';//用户答案
                question.isFavorite='false';//收藏
                question.isNote='false';//笔记
                question.correct=-1;//0为正确 1为错误
                question.personDoNum=0;//个人做题数
                question.personErrorNum=0;//个人做错题数

            });
        }

       return resultArray;
}
/***id查询表数据并组装**/
export function queryFromRealmWithIds(questions,type) {

        let queryStr='';
        questions.forEach((obj,i) => {
            queryStr+='id = "'+obj.idStr+'"';
            if(i!=questions.length-1){
                queryStr+=' or ';
            }
        });

        let questionObj = realm.objects('Question').filtered(queryStr);
        let objStr = JSON.stringify(questionObj);
        let result=JSON.parse(objStr);
        let resultArray=[];
        let resultMap={};
        if(result!=undefined){

            Array.from(result).forEach((obj,i) => {
                obj.idStr=obj.id;
                resultMap[obj.id]=obj;
            });
            questions.forEach((obj,i) => {
                let question=resultMap[obj.idStr];
                if(question){
                    question.doNum=obj.doNum;//全站做题数
                    question.errorNum=obj.errorNum;//全站做错题数
                    question.firstNum=obj.firstNum;//第一次做题人数
                    question.firstErrorNum=obj.firstErrorNum;//第一次做错题人数
                    question.commentNum=obj.commentNum;//回复数
                    question.userAnswer=obj.userAnswer;//用户答案
                    question.isFavorite=obj.isFavorite;//收藏
                    question.isNote=obj.isNote;//笔记
                    question.correct=obj.correct;//0为正确 1为错误
                    question.personDoNum=obj.personDoNum;//个人做题数
                    question.personErrorNum=obj.personErrorNum;//个人做错题数
                    question.materialIdStr=obj.materialIdStr;//材料题id
                    resultArray.push(question);
                    resultMap[obj.idStr]=question;
                }

            });
        }

        if(type=='LIST'){
            return resultArray;
        }else{
            return resultMap;
        }

}




/***批量写入表数据**/
export function writeArrayToRealm(array) {

    realm.write(() => {
        array.forEach(obj => {
            realm.create('Question', obj, true);
        });
    })

}

