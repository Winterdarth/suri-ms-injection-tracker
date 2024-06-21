document.addEventListener('DOMContentLoaded', async function() {
    const currentDate = document.getElementById('current-date');
    const currentBodyPart = document.getElementById('current-body-part');
    const nextDate = document.getElementById('next-date');
    const nextBodyPart = document.getElementById('next-body-part');

    async function fetchInjectionSchedule() {
        try {
            const response = await fetch('/api/schedules');
            if (!response.ok) {
                throw new Error('Failed to fetch injection schedule data');
            }
            return await response.json();
        } catch (error) {
            console.error('Error fetching injection schedule data:', error);
            return [];
        }
    }

    function renderSchedule(scheduleData) {
        if (scheduleData.length >= 2) {
            currentDate.textContent = scheduleData[0].injectionDate;
            currentBodyPart.textContent = scheduleData[0].bodyPart;
            nextDate.textContent = scheduleData[1].injectionDate;
            nextBodyPart.textContent = scheduleData[1].bodyPart;
        } else if (scheduleData.length === 1) {
            const injection = scheduleData[0];
            currentDate.textContent = injection.injectionDate;
            currentBodyPart.textContent = injection.bodyPart;
            nextDate.textContent = injection.injectionDate;
            nextBodyPart.textContent = injection.bodyPart;
        } else {
            console.error('No injection schedule data found');
        }
    }

    async function markDone() {
        try {
            const scheduleData = await fetchInjectionSchedule();
            if (scheduleData.length === 0) {
                console.error('No injection schedule data found');
                return;
            }
            const currentScheduleId = scheduleData[0].id;
            const response = await fetch(`/api/schedules/${currentScheduleId}/complete`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ id: currentScheduleId })
            });
            if (!response.ok) {
                throw new Error('Failed to mark injection as done');
            }
            await renderSchedule(scheduleData);
            // Disable the button and visually indicate it has been used
            markDoneBtn.disabled = true;
            markDoneBtn.textContent = 'Már Beadva';
            markDoneBtn.classList.add('used');
        } catch (error) {
            console.error('Error marking injection as done:', error);
        }
    }

    const markDoneBtn = document.getElementById('mark-done-btn');
    if (markDoneBtn) {
        markDoneBtn.addEventListener('click', markDone);
    } else {
        console.error('mark-done-btn not found');
    }

    renderSchedule(await fetchInjectionSchedule());
});
