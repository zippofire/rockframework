/*
* This file is part of rockframework.
* 
* rockframework is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 3 of the License, or
* (at your option) any later version.
* 
* rockframework is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>;.
*/

// Table
jQuery.fn.addRows = function(array, contentFunctions, options) {
	if((options == undefined) || (options == null)) {
        options = {};
    }
	if(options.rowTemplate == null) {
        options.rowTemplate = "<tr></tr>";
    }
	if(options.columnTemplate == null) {
        options.columnTemplate = "<td></td>";
    }
	for(var i = 0; i < array.length; i++) {
        var obj = array[i];
		var tmp = this;
		
		if(typeof options.rowTemplate == 'function') {
            var rowOptions = {value: obj, rowIndex: i};
            this.append(options.rowTemplate(rowOptions));
        }
        else {
            this.append(options.rowTemplate);
        }
        
		var tr = this.children(":last-child");
		
        for(var j = 0; j < contentFunctions.length; j++) {
            if(typeof options.columnTemplate == 'function') {
	            var columnOptions = {value: obj, rowIndex: i, columnIndex: j};
                tr.append(options.columnTemplate(columnOptions));
	        }
	        else {
	            tr.append(options.columnTemplate);
	        }
            var td = tr.children(":last-child");
            var content = contentFunctions[j](obj);
            td.append(content);
        }
    }
	
	return this;
};

jQuery.fn.removeRows = function() {
    this.empty();
    return this;
};

// Select
jQuery.fn.addOptions = function(array, id, value) {
    for(var i = 0; i < array.length; i++) {
    	var obj = array[i];
    	var localId = "";
    	var localValue = "";
    	if(typeof id == 'function') {
    		localId = id(obj);	
    	}
    	else {
    		localId = obj[id];
    	}
    	
    	if(typeof value == 'function') {
    		localValue = value(obj);
    	}
    	else {
    		localValue = obj[value];
    	}
    	
        var option = "<option value=\"" + localId + "\">" + localValue + "</option>";
        this.append(option);
    }
    return this;
};

jQuery.fn.removeOptions = function() {
    this.empty();
    return this;
};