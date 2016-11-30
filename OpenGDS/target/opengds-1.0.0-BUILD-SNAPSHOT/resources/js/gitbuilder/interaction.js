/**
 * 공간정보 편집도구를 구성하는 함수를 정의한다.(Interaction)
 * 
 * @author yijun.so
 * @date 2016. 10. 21
 * @version 0.01
 */

gitbuilder.interaction = {};

gitbuilder.interaction.deleteVertex = function () {
	ol.interaction.Modify.call(this, {
		condition : gitbuilder.interaction.deleteVertex.prototype.condition
	});
}