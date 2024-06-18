const wbook = SpreadsheetApp.open(DriveApp.getFileById("1_UFkboYsAoLA-cHykXPwM0GxUiUG50UejuYB8H2HGG0"));
const sheet = wbook.getSheetByName('daftarnama');

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
  
  if (action == "register") {
    return register(data);
  } else if (action == "login") {
    return login(data);
  } else if (action == "updatePassword") {
    const id = e.parameter.id;
    return updatePasswordById(id, data);
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

function register(data) {
  const email = data.email;
  const existingUser = findUserByEmail(email);
  
  if (existingUser) {
    let response = {
      "success": false,
      "message": "Email already registered"
    };
    return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
  }
  
  sheet.appendRow([
    uniqueId('daftarnama'),
    data.namaDepan,
    data.namaBelakang,
    data.email,
    data.sandi,
    data.role
  ]);
  let response = {
    "success": true,
    "message": "Success add data",
    "data": data
  };
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function login(data) {
  const email = data.email;
  const sandi = data.sandi;
  const user = findUserByEmail(email);
  
  if (!user) {
    let response = {
      "success": false,
      "message": "Email not found"
    };
    return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
  }
  
  if (user.sandi !== sandi) {
    let response = {
      "success": false,
      "message": "Incorrect password"
    };
    return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
  }
  
  let response = {
    "success": true,
    "message": "Login successful",
    "data": user
  };
  return ContentService.createTextOutput(JSON.stringify(response)).setMimeType(ContentService.MimeType.JSON);
}

function updatePasswordById(id, data) {
  const rlen = sheet.getLastRow();
  const clen = sheet.getLastColumn();
  const row = sheet.getRange(1, 1, rlen, clen).getValues();
  
  for (let i = 1; i < rlen; i++) {
    if (row[i][0] == id) {
      sheet.getRange(i + 1, 5).setValue(data.sandi); // Assuming 'sandi' is in the 5th column
      let response = {
        "success": true,
        "message": "Success update password",
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

function findUserByEmail(email) {
  const rlen = sheet.getLastRow();
  const row = sheet.getRange(1, 1, rlen, sheet.getLastColumn()).getValues();
  
  for (let i = 1; i < rlen; i++) {
    if (row[i][3] === email) { // Assuming 'email' is in the 4th column
      let user = {};
      for (let j = 0; j < row[0].length; j++) {
        user[row[0][j]] = row[i][j];
      }
      return user;
    }
  }
  return null;
}

function uniqueId(table = 'default') {
  let prop = PropertiesService.getScriptProperties().getProperty(table);
  let current = prop != null ? prop : 0;
  let value = parseInt(current) + 1;
  PropertiesService.getScriptProperties().setProperty(table, value);
  Logger.log(value);
  return value;
}
