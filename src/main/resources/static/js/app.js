document.addEventListener('DOMContentLoaded', function() {
    // Dummy injection schedule data (replace with actual data from backend)
    const scheduleData = [
        { bodyPart: 'Bal Kar', injectionDate: '2024-02-17', injectionCompleted: false },
        { bodyPart: 'Jobb Kar', injectionDate: '2024-02-18', injectionCompleted: false },
        { bodyPart: 'Bal Comb', injectionDate: '2024-02-19', injectionCompleted: false }
    ];

    // Function to find the next injection schedule
    function findNextInjection() {
        const currentDate = new Date().toISOString().split('T')[0];
        const nextInjection = scheduleData.find(schedule => schedule.injectionDate > currentDate);
        return nextInjection;
    }

    // Function to render today's injection and next injection
    function renderSchedule() {
        const currentDate = new Date().toLocaleDateString('hu-HU', { year: 'numeric', month: '2-digit', day: '2-digit' }).split('.').join('.');
        document.getElementById('current-date').textContent = `Dátum: ${currentDate}`;

        const currentInjection = scheduleData.find(schedule => schedule.injectionDate === currentDate);
        if (currentInjection) {
            document.getElementById('current-body-part').textContent = `Beadási hely: ${currentInjection.bodyPart}`;
        } else {
            document.getElementById('current-body-part').textContent = 'Nincs mára beadás ütemezve';
            document.getElementById('mark-done-btn').disabled = true;
        }

        const nextInjection = findNextInjection();
        if (nextInjection) {
            const nextDate = new Date(nextInjection.injectionDate).toLocaleDateString('hu-HU', { year: 'numeric', month: '2-digit', day: '2-digit' }).split('.').join('.');
            document.getElementById('next-date').textContent = `Dátum: ${nextDate}`;
            document.getElementById('next-body-part').textContent = `Következő beadási hely: ${nextInjection.bodyPart}`;
        } else {
            document.getElementById('next-date').textContent = 'Nincs következő beadás';
            document.getElementById('next-body-part').textContent = '';
        }
    }

    // Function to mark current injection as done
    function markDone() {
        const currentDate = new Date().toLocaleDateString('hu-HU', { year: 'numeric', month: '2-digit', day: '2-digit' }).split('.').join('.');
        const currentInjectionIndex = scheduleData.findIndex(schedule => schedule.injectionDate === currentDate);
        if (currentInjectionIndex !== -1) {
            scheduleData[currentInjectionIndex].injectionCompleted = true;
            renderSchedule();
            // Make an API call to update the backend with the marked injection status
        }
    }

    // Event listener for the mark as done button
    document.getElementById('mark-done-btn').addEventListener('click', markDone);

    // Initial rendering of schedule
    renderSchedule();
});
