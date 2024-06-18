const wbook = SpreadsheetApp.open(DriveApp.getFileById("1_UFkboYsAoLA-cHykXPwM0GxUiUG50UejuYB8H2HGG0"));
const sheet = wbook.getSheetByName('perkaraPenting');

function doGet(e) {
  const id = e.parameter.id;
  if (id) {
    return getById(id);
  } else {
    return getAll();
  }
}

function doPost(e) {
  const action = e.parameter.action;
  const data = JSON.parse(e.postData.contents);
  
  if (action == "insert") {
    return insert(data);
  } else if (action == "update") {
    const id = e.parameter.id;
    return updateById(id, data);
  } else if (action == "delete") {
    const id = e.parameter.id;
    return deleteById(id);
  }
}

function getAll() {
  let data = [];
  const rlen = sheet.getLastRow();
  const clen = sheet.getLastColumn();
  const row = sheet.getRange(1, 1, rlen, clen).getValues();
  for (let i = 0; i < row.length; i++) {
    const dataRow = row[i];
    let record = {};
    for (let j = 0; j < clen; j++) {
      record[row[0][j]] = dataRow[j];
    }
    if (i > 0) {
      data.push(record);
    }
  }
  const response = {
    "data": data
  };
  console.log(response);
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function getById(id) {
  const rlen = sheet.getLastRow();
  const clen = sheet.getLastColumn();
  const row = sheet.getRange(1, 1, rlen, clen).getValues();
  let data = {};
  for (let i = 1; i < rlen; i++) {
    if (row[i][0] == id) {
      for (let j = 0; j < clen; j++) {
        data[row[0][j]] = row[i][j];
      }
      break;
    }
  }
  const response = {
    "data": data
  };
  console.log(response);
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function insert(data) {
  sheet.appendRow([
    uniqueId('perkaraPenting'),
    data.Judul_Perkara,
    data.Kasus_Posisi,
    data.Identitas_tersangka,
    data.Pasal,
    data.Penahanan,
  ]);
  let response = {
    "success": true,
    "message": "Success add data",
    "data": data
  };
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function updateById(id, data) {
  const rlen = sheet.getLastRow();
  const clen = sheet.getLastColumn();
  const row = sheet.getRange(1, 1, rlen, clen).getValues();
  
  for (let i = 1; i < rlen; i++) {
    if (row[i][0] == id) {
      sheet.getRange(i + 1, 2, 1, clen - 1).setValues([[
      data.Judul_Perkara,
      data.Kasus_Posisi,
      data.Identitas_tersangka,
      data.Pasal,
      data.Penahanan,
      ]]);
      let response = {
        "success": true,
        "message": "Success update data",
        "data": data
      };
      return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
    }
  }
  
  let response = {
    "success": false,
    "message": "ID not found"
  };
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function deleteById(id) {
  const rlen = sheet.getLastRow();
  const row = sheet.getRange(1, 1, rlen, 1).getValues();
  
  for (let i = 1; i < rlen; i++) {
    if (row[i][0] == id) {
      sheet.deleteRow(i + 1);
      let response = {
        "success": true,
        "message": "Success delete data"
      };
      return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
    }
  }
  
  let response = {
    "success": false,
    "message": "ID not found"
  };
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function uniqueId(table = 'default') {
  let prop = PropertiesService.getScriptProperties().getProperty(table);
  let current = prop != null ? prop : 0;
  let value = parseInt(current) + 1;
  PropertiesService.getScriptProperties().setProperty(table, value);
  Logger.log(value);
  return value;
}
