var random = require("random-js")(); // uses the nativeMath engine

function randomAddressBook() {
    return createAddressBook();
}

function createAddressBook() {
    var description = random.string(300);
    var person1 = createPerson();
    var person2 = createPerson();
    var person3 = createPerson();
    return {
        description: description,
        person: [person1, person2, person3]
    };
}

function toAddressBook(data) { //string
    return JSON.parse(data);
}

function toString(addressBook) {
    return JSON.stringify(addressBook);
}


function createPerson() {
    var phoneNumber1 = createPhoneNumber();
    var phoneNumber2 = createPhoneNumber();
    var name = random.string(25);
    var email = random.string(20);
    var id = random.integer(0, 10000000);
    return {
        name: name,
        id: id,
        email: email,
        phoneNumber: [phoneNumber1, phoneNumber2]
    }
}

function createPhoneNumber() {
    var phoneNumber = random.string(10, '0123456789');
    var phoneType = random.pick(['MOBILE', 'HOME', 'WORK']);
    return {
        phoneNumber: phoneNumber,
        phoneType: phoneType
    };
}

//var anAddressBook = randomAddressBook();
//console.log(JSON.stringify(anAddressBook, null, "  "));
//console.log(toString(anAddressBook));
//console.log(toAddressBook(toString(anAddressBook)));
