if (survey.controls.list[x].subControls.list.length != 0) {
                                var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
                                var c = $("<tr class=\"table-matrix-header\"></tr>");
                                var d = $("<td>&nbsp;</td>");
                                c.append(d);
                                for (var y = 0; y < survey.controls.list[x].options.list.length; y++) {
                                    var e = $("<th>" + survey.controls.list[x].options.list[y].text + "</th>");
                                    c.append(e)
                                }
                                b.append(c);
                                for (var i = 0; i < survey.controls.list[x].subControls.list.length; i++) {
                                    var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
                                    var g = $("<th>" + survey.controls.list[x].subControls.list[i].content + "</th>");
                                    f.append(g);
                                    for (var y = 0; y < survey.controls.list[x].options.list.length; y++) {
                                        var h = null;
                                        if (survey.controls.list[x].inputTypeId == 0) {
                                            h = $("<td><lable><input type=\"radio\" name=\"" + (survey.controls.list[x].controlId + "_" + survey.controls.list[x].subControls.list[i].controlId) + "\" /></label></td>")
                                        } else if (survey.controls.list[x].inputTypeId == 1) {
                                            h = $("<td><lable><input type=\"checkbox\" /></label></td>")
                                        }
                                        f.append(h)
                                    }
                                    b.append(f)
                                }
                                a.append(b)
                            } else {
                            	
                                var b = $("<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" class=\"table-matrix\"></table>");
                                var c = $("<tr class=\"table-matrix-header\"></tr>");
                                var d = $("<td>&nbsp;</td>");
                                c.append(d);
                                
                                for (var y = 0; y < 5; y++) {
                                    var e = $("<th>" + I + " " + (y + 1) + "</th>");
                                    c.append(e)
                                }
                                
                                b.append(c);
                                for (var i = 0; i < 1; i++) {
                                    var f = $("<tr " + (i % 2 ? "style=\"background: #efefef\"" : "") + "></tr>");
                                    var g = $("<th>" + H + " " + (i + 1) + "</th>");
                                    f.append(g);
                                    for (var y = 0; y < 5; y++) {
                                        var h = null;
                                        if (survey.controls.list[x].inputTypeId == 0) {
                                            h = $("<td><lable><input type=\"radio\" name=\"" + (survey.controls.list[x].controlId + "_" + i) + "\" /></label></td>")
                                        } else if (survey.controls.list[x].inputTypeId == 1) {
                                            h = $("<td><lable><input type=\"checkbox\" /></label></td>")
                                        }
                                        f.append(h)
                                    }
                                    b.append(f)
                                }
                                a.append(b)
                            }