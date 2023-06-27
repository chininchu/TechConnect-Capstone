const client = filestack.init('ACwPbXBSRRqPzfzlgNQu2z');
const uploadButton = document.getElementById('image-upload');

uploadButton.addEventListener('click', (event) => {
    event.preventDefault();
    const pickerOptions = {
        onFileUploadFinished: (file) => {
            // Handle the uploaded file response here
            console.log('Uploaded file:', file);
            $("#profilePic").val(file.url);

            // We will use the .val method in Jquery method to grab the url by the Id selector

            // You can access the uploaded file details like filename, handle, URL, etc.
            // saveProfilePicture(file.url);
        },
        onUploadDone: (result) => {
            // Handle the completion of all uploads
            console.log('All uploads finished');
            // You can access the uploaded files' details from the result object
            const filesUploaded = result.files;
            // Do any additional processing or validation here
        },
    };
    const picker = client.picker(pickerOptions);
    picker.open();
});

// function saveProfilePicture(fileUrl) {
//     const formData = new FormData();
//     formData.append('profilePicture', fileUrl);
//
//     // Send the formData with the file URL to the server
//     fetch('/SignUpPage', {
//         method: 'POST',
//         body: formData
//     }).then((response) => {
//         // Handle the response from the server
//         console.log('Save profile picture response:', response);
//         // Continue with user registration or perform any other necessary actions
//     }).catch((error) => {
//         // Handle any errors that occur during saving the file URL
//         console.error('Save profile picture error:', error);
//     });
// }
